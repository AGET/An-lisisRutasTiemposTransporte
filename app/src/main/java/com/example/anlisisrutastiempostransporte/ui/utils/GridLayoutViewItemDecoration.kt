package com.example.anlisisrutastiempostransporte.ui.utils

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import kotlin.math.ceil

internal object GridLayoutViewItemDecoration {
    fun getItemOffsets(
        outRect: Rect,
        gridLayoutManager: GridLayoutManager,
        position: Int,
        itemCount: Int,
        space: Int
    ) {
        val cols = gridLayoutManager.spanCount
        val rows = ceil(itemCount / cols.toDouble()).toInt()

        outRect.top = space
        outRect.left = space
        if (position % cols == cols - 1) {
            outRect.right = space
        } else {
            outRect.right = 0
        }

        if (position / cols == rows - 1) {
            outRect.bottom = space
        } else {
            outRect.bottom = 0
        }
    }
}
