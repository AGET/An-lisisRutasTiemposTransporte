package com.example.anlisisrutastiempostransporte.ui.itinerary

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.anlisisrutastiempostransporte.R
import com.example.anlisisrutastiempostransporte.databinding.ItemItineraryListBinding
import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryItemModel
import com.example.anlisisrutastiempostransporte.utils.bindingInflate

class ItineraryAdapter(val context: Context) :
    RecyclerView.Adapter<ItineraryAdapter.ItineraryListViewHolder>() {

    private val itemList: MutableList<ItineraryItemModel> = mutableListOf()

    fun updateData(newData: List<ItineraryItemModel>) {
        itemList.clear()
        itemList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ItineraryListViewHolder(
        context,
        viewGroup.bindingInflate(R.layout.item_itinerary_list, false)
    )

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(viewHolder: ItineraryListViewHolder, position: Int) {
        viewHolder.bind(itemList[position])
    }

    class ItineraryListViewHolder(
        val context: Context,
        private val dataBinding: ItemItineraryListBinding,
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        //region Public Methods
        fun bind(item: ItineraryItemModel) {
            //ResourcesCompat.getDrawable(resources, R.drawable.custom_progress_bar, null))
            dataBinding.icon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    item.icon
                )
            )
            dataBinding.tvTitle.text = item.title
            dataBinding.tvDetail.text = item.detail
        }
    }
}
