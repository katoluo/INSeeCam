package cn.kato.inseecam.ui.component.devices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import cn.kato.inseecam.R
import cn.kato.inseecam.databinding.DeviceListActivityBinding

class DeviceListActivity : AppCompatActivity() {

    private val viewModel by viewModels<DeviceListViewModel>()
    private lateinit var binding: DeviceListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.device_list_activity)

        binding.viewModel = viewModel
    }
}