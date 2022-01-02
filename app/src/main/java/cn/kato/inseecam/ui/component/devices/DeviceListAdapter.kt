package cn.kato.inseecam.ui.component.devices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.kato.inseecam.databinding.DeviceListItemBinding

/**
 * @author  :   kato
 * time     :   2022-01-02
 * desc     :   https://medium.com/evan-android-note/android-mvvm-recyclerview-3d3d6f08df85
 */
class DeviceListAdapter(
    private val viewModel: DeviceListViewModel,
) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = viewModel.wifiData.value!![position]
        holder.bind(viewModel, bean)
    }

    override fun getItemCount() = viewModel.wifiData.value?.size ?: 0

    class ViewHolder private constructor(private val binding: DeviceListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DeviceListViewModel, bean: WifiBean) {
            binding.viewModel = viewModel
            binding.wifiBean = bean
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DeviceListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}