package com.example.lists_2

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = 10.fromDpToPixels(context)
        with(outRect) {
            top = offset
            bottom = offset
            left = offset
            right = offset
        }
    }
}