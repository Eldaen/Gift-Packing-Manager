package com.example.giftpackinghelper

class Calculator {

    class PaperSize(val width: Double, val height: Double)

    fun calculate (width: Double, height: Double, depth: Double): PaperSize {
        val sizes = listOf(width, height, depth).sorted()
        val (correctedDepth, correctedWidth, correctedHeight) = sizes

        val extraWidth = if (correctedDepth / 2 > 3) correctedDepth / 2 else EXTRA_WIDTH

        return PaperSize(
            2 * correctedWidth + 2 * correctedDepth + extraWidth,
            correctedHeight + (4 * correctedDepth) / 3
        )
    }
}

private val EXTRA_WIDTH = 3.0