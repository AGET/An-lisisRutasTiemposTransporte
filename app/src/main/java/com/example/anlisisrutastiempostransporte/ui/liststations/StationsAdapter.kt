package com.example.anlisisrutastiempostransporte.ui.liststations

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.ItemStationListBinding
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.utils.bindingInflate

class StationsAdapter : RecyclerView.Adapter<StationsAdapter.StationsListViewHolder>() {
    private val stationList: MutableList<Station> = mutableListOf()

    fun updateData(newData: List<Station>) {
        stationList.clear()
        stationList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = StationsListViewHolder(
        viewGroup.bindingInflate(R.layout.item_station_list, false)
    )

    override fun getItemCount() = stationList.size

    override fun getItemId(position: Int): Long = stationList[position].id.toLong()

    override fun onBindViewHolder(viewHolder: StationsListViewHolder, position: Int) {
        viewHolder.bind(stationList[position])
    }

    class StationsListViewHolder(
        private val dataBinding: ItemStationListBinding,
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(item: Station) {
            dataBinding.tvName.text = item.name
            dataBinding.tvDistance.text = "${item.startTerminalLength} Km"
        }
    }
}
