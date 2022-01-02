package cn.kato.inseecam.ui.component.devices

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.kato.inseecam.R
import cn.kato.inseecam.databinding.DeviceListActivityBinding
import cn.kato.inseecam.utils.DoubleClickUtils
import cn.kato.inseecam.utils.showShort
import com.afollestad.materialdialogs.MaterialDialog
import com.gyf.immersionbar.ktx.immersionBar
import com.permissionx.guolindev.PermissionX
import timber.log.Timber

/**
 *  @author :   kato
 *  time    :   2022-01-01
 */
class DeviceListActivity : AppCompatActivity(R.layout.device_list_activity), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: DeviceListActivityBinding
    private val _viewModel by viewModels<DeviceListViewModel>()
    private val _wifiManager by lazy { applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    private lateinit var _adapter: DeviceListAdapter

    /** 广播接收器 */
    private val _wifiReceive = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> getScanResults(
                    // api23及以上，包含这个额外数据来指明此次扫描是否成功
                    intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED,
                        false
                    )
                )
            }
        }
    }
    /** 创建需要接受的广播过滤器 */
    private fun createIntentFilter() =
        IntentFilter().apply {
            addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        }

    /** 获取扫描结果 */
    private fun getScanResults(success: Boolean) {
        Timber.d("描扫结束，处理数据")
        _viewModel.setWifiData(
            if (success) {
                Timber.d("成功获取到新的扫描数据")
                _wifiManager.scanResults
            } else {
                Timber.e("获取扫描数据失败")
                showShort(this, "扫描失败")
                null
            }
        )
        binding.swipe.isRefreshing = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.device_list_activity)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        initBars()

        // 设置下拉刷新事件
        binding.swipe.setOnRefreshListener(this)
        // 创建recyclerView的Adapter
        _adapter = DeviceListAdapter(_viewModel)
        // 观察数据
        _viewModel.wifiData.observe(this) {
            _adapter.notifyDataSetChanged()
            binding.recycler.apply {
                layoutAnimation = AnimationUtils.loadLayoutAnimation(this@DeviceListActivity, R.anim.layout_fall_down)
                scheduleLayoutAnimation()
            }
        }
        // 在activity中观察item点击事件
        _viewModel.clickEvent.observe(this) {
            showShort(this, "点击了${it.peekContent().ssid}")
        }
        // 在activity中观察item长按事件
        _viewModel.longClickEvent.observe(this) {
            MaterialDialog(this).show {
                title(text = it.peekContent().ssid)
                message(text = "长按了${it.peekContent().ssid}")
                positiveButton(text = "POSITIVE") {
                    showShort(this@DeviceListActivity, "点击了积极键")
                }
                negativeButton(text = "NEGATIVE") {
                    showShort(this@DeviceListActivity, "点击了消极键")
                }
            }
        }
        // 为recyclerView设置适配器
        binding.recycler.adapter = _adapter
    }

    override fun onResume() {
        super.onResume()
        // 注册广播接收器
        registerReceiver(_wifiReceive, createIntentFilter())
    }

    override fun onPause() {
        super.onPause()
        // 注销广播接收器
        unregisterReceiver(_wifiReceive)
    }

    override fun onBackPressed() {
        if (DoubleClickUtils.isOnDoubleClick()) super.onBackPressed()
        else showShort(this, "在按一次退出")
    }


    override fun onRefresh() {
        if (isGrantedLocation()) {
            Timber.d("开始扫描wifi")
            _wifiManager.startScan()
        } else {
            binding.swipe.isRefreshing = false
            requestLocation()
        }
    }

    /** 初始化状态栏、标题栏、导航栏 */
    private fun initBars() {
        // Toolbar https://blog.csdn.net/jaynm/article/details/106727790
        // 初始化标题栏菜单和点击事件
        binding.toolbar.inflateMenu(R.menu.device_list_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.album -> showShort(this, "Album")
                R.id.about_app -> showShort(this, "About")
            }
            false
        }
        // 设置状态栏
        immersionBar {
            statusBarDarkFont(true)
            titleBar(R.id.toolbar)
            autoDarkModeEnable(true, 0.2f)
        }
    }

    /** 是否已经授予了位置权限 */
    private fun isGrantedLocation() =
        PermissionX.isGranted(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                PermissionX.isGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    /** 申请位置权限 */
    private fun requestLocation() {
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    showShort(this, "位置权限已授予")
                } else {
                    showShort(this, "拒绝了位置权限")
                }
            }
    }
}