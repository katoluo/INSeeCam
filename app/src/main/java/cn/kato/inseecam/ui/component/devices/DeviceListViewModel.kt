package cn.kato.inseecam.ui.component.devices

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class DeviceListViewModel : ViewModel() {

    private val _wifiData = MutableLiveData<MutableList<WifiBean>>()
    val wifiData: LiveData<MutableList<WifiBean>> get() = _wifiData
    // Transformations.map()方法可以对LiveData的数据类型进行转换
    // LiveData<MutableList<WifiBean>> ===>> LiveData<Boolean>
    // 若数据为空则显示空布局，否则显示RecyclerView
    val empty: LiveData<Boolean> = Transformations.map(wifiData) {
        it.isEmpty()
    }

    /** 对外提供设置数据 */
    fun setWifiData(results: MutableList<ScanResult>?) {
        val list = mutableListOf<WifiBean>()
        results?.forEach {
            if (it.SSID.isNotEmpty()) {
                list.add(
                    WifiBean(
                        it.SSID,
                        // 计算wifi信号强度 https://www.itranslater.com/qa/details/2583831304663991296
                        WifiManager.calculateSignalLevel(it.level, 5),
                        it.capabilities.contains("APK")
                    )
                )
            }
        }
        _wifiData.value = list
    }

    // 单击事件
    private val _clickEvent = MutableLiveData<Event<WifiBean>>()
    val clickEvent: LiveData<Event<WifiBean>> get() = _clickEvent
    fun clickItem(bean: WifiBean) {
        _clickEvent.value = Event(bean)
    }

    // 长按事件
    private val _longClickEvent = MutableLiveData<Event<WifiBean>>()
    val longClickEvent: LiveData<Event<WifiBean>> get() = _longClickEvent
    fun longClickItem(bean: WifiBean): Boolean {
        _longClickEvent.value = Event(bean)
        return false
    }

}

data class WifiBean(
    // wifi名
    val ssid: String,
    // wifi信号
    val signal: Int,
    // 是否需要认证
    val auth: Boolean
)