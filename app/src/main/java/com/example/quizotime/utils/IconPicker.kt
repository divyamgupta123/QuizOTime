package com.example.quizotime.utils

import com.example.quizotime.R

object IconPicker {

    private val colors = arrayOf(
        R.drawable.ic_icon_1,
        R.drawable.ic_icon_2,
        R.drawable.ic_icon_3,
        R.drawable.ic_icon_4,
        R.drawable.ic_icon_5,
        R.drawable.ic_icon_6,
        R.drawable.ic_icon_7,
        R.drawable.ic_icon_8,
        R.drawable.ic_icon_9
    )
    var currentIconIndex = 0
    fun getIcon(): Int {
        currentIconIndex = (currentIconIndex + 1) % colors.size
        return colors[currentIconIndex]
    }
}