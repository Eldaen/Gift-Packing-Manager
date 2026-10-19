package com.example.giftpackinghelper

/** All dimensions, including the paper overlap, are in centimetres. */
class Calculator {
    class PaperSize(val width: Double, val height: Double)

    fun calculate(width: Double, height: Double, depth: Double): PaperSize {
        require(listOf(width, height, depth).all { it.isFinite() && it > 0 }) {
            "Gift dimensions must be positive finite numbers."
        }
        val (shortest, middle, longest) = listOf(width, height, depth).sorted()
        val overlap = maxOf(shortest / 2, 3.0)
        val paper = PaperSize(
            width = 2 * middle + 2 * shortest + overlap,
            height = longest + shortest * (4.0 / 3.0),
        )
        require(paper.width.isFinite() && paper.height.isFinite()) {
            "Gift dimensions are too large."
        }
        return paper
    }

    /** A shared input boundary for both UIs; accepts decimal comma and point. */
    fun calculateOrNull(width: String, height: String, depth: String): PaperSize? {
        val w = parseDimension(width) ?: return null
        val h = parseDimension(height) ?: return null
        val d = parseDimension(depth) ?: return null
        return try {
            calculate(w, h, d)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    private fun parseDimension(value: String): Double? =
        value.trim().replace(',', '.').toDoubleOrNull()?.takeIf { it.isFinite() && it > 0 }
}
