package com.example.giftpackinghelper

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CalculatorTest {
    private val calculator = Calculator()

    @Test fun computesPaperWithMinimumOverlap() {
        val result = calculator.calculate(10.0, 20.0, 5.0)
        assertEquals(33.0, result.width, 0.00001)
        assertEquals(26.6666666667, result.height, 0.00001)
    }

    @Test fun orientationDoesNotChangePaperSize() {
        listOf(
            listOf(5.0, 10.0, 20.0), listOf(5.0, 20.0, 10.0),
            listOf(10.0, 5.0, 20.0), listOf(10.0, 20.0, 5.0),
            listOf(20.0, 5.0, 10.0), listOf(20.0, 10.0, 5.0),
        ).forEach { (w, h, d) ->
            val result = calculator.calculate(w, h, d)
            assertEquals(33.0, result.width, 0.00001)
            assertEquals(26.6666666667, result.height, 0.00001)
        }
    }

    @Test fun increasesOverlapForThickGifts() {
        val result = calculator.calculate(12.0, 20.0, 30.0)
        assertEquals(70.0, result.width)
        assertEquals(46.0, result.height)
    }

    @Test fun acceptsDecimalCommaPointAndWhitespace() {
        val result = assertNotNull(calculator.calculateOrNull(" 10,5 ", "20.5", "5"))
        assertEquals(34.0, result.width)
        assertEquals(27.1666666667, result.height, 0.00001)
    }

    @Test fun rejectsEmptyMalformedNonPositiveAndNonFiniteInputs() {
        listOf("", " ", "abc", "0", "-1", "NaN", "Infinity", "1,2,3", "1e309").forEach {
            assertNull(calculator.calculateOrNull(it, "10", "20"))
            assertNull(calculator.calculateOrNull("10", it, "20"))
            assertNull(calculator.calculateOrNull("10", "20", it))
        }
        assertNull(calculator.calculateOrNull("1e308", "1e308", "1e308"))
        assertFailsWith<IllegalArgumentException> { calculator.calculate(0.0, 10.0, 20.0) }
    }
}
