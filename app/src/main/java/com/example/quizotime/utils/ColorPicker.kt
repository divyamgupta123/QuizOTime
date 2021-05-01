package com.example.quizotime.utils

object ColorPicker {
    private val colors = arrayOf(
        "#3EB9Df",
        "#3685BC",
        "#D36280",
        "#E44F55",
        "#FA8056",
        "#818BCA",
        "#7D6595",
        "#51BAB3",
        "#4FB66C",
        "#E3AD17"
    )
    var currentColorIndex = 0
    fun getColor(): String {
        currentColorIndex = (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]
    }
}