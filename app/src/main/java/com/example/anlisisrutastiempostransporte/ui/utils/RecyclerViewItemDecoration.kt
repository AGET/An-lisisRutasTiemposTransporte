package com.example.anlisisrutastiempostransporte.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerViewItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            GridLayoutViewItemDecoration.getItemOffsets(
                outRect,
                layoutManager,
                parent.getChildViewHolder(view).getAbsoluteAdapterPosition(),
                state.itemCount,
                space
            )
        } else if (layoutManager is LinearLayoutManager) {
            LinearLayoutViewItemDecoration.getItemOffsets(
                outRect,
                layoutManager,
                parent.getChildViewHolder(view).getAbsoluteAdapterPosition(),
                state.itemCount,
                space
            )
        }
    }
}
