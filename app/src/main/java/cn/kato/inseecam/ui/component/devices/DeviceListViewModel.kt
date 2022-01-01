package cn.kato.inseecam.ui.component.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeviceListViewModel : ViewModel() {

    private val _wifiData = MutableLiveData<MutableList<WifiBean>>()
    val wifiData: LiveData<MutableList<WifiBean>> get() = _wifiData

}

data class WifiBean(
    val ssid: String,
    val signal: Int,
    val type: String
)