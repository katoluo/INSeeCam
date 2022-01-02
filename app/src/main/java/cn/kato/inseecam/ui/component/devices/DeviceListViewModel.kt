package cn.kato.inseecam.ui.component.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeviceListViewModel : ViewModel() {

    private val _wifiData = MutableLiveData<MutableList<WifiBean>>()
    val wifiData: LiveData<MutableList<WifiBean>> get() = _wifiData

    fun setWifiData(wifiData: MutableList<WifiBean>) {
        _wifiData.value = wifiData
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