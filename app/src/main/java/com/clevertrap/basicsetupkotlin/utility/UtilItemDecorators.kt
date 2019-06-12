package com.wizy.bvrstudent.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration

object UtilItemDecorators {

    fun getSpaceDecorator(context: Context,
                          shapeDrawable: Int): DividerItemDecoration {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context, shapeDrawable)!!)
        return itemDecorator
    }
}
