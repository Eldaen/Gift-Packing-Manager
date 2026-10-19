package com.example.giftpackinghelper.android

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "ru-rRU-w402dp-h874dp-xhdpi")
class CalculatorUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun rendersAndCalculatesThenResets() {
        compose.setContent { MyApplicationTheme { GiftPackingApp() } }
        compose.onNodeWithText("Рассчитать").assertIsNotEnabled()
        compose.onRoot().captureRoboImage("build/ui-check/android-input.png")
        compose.onNodeWithContentDescription("Высота").performTextInput("20")
        compose.onNodeWithContentDescription("Ширина").performTextInput("10")
        compose.onNodeWithContentDescription("Глубина").performTextInput("5")
        compose.onNodeWithText("Рассчитать").assertIsEnabled().performClick()
        compose.onNodeWithText("26,7").assertIsDisplayed()
        compose.onNodeWithText("33,0").assertIsDisplayed()
        compose.onRoot().captureRoboImage("build/ui-check/android-result.png")
        compose.onNodeWithText("Новый подарок").performClick()
        compose.onNodeWithText("Рассчитать").assertIsNotEnabled()
        compose.onNodeWithContentDescription("Высота").performTextInput("20,5")
        compose.onNodeWithContentDescription("Ширина").performTextInput("10,5")
        compose.onNodeWithContentDescription("Глубина").performTextInput("5")
        compose.onNodeWithText("Рассчитать").performClick()
        compose.onNodeWithText("34,0").assertIsDisplayed()
        compose.onNodeWithText("27,2").assertIsDisplayed()
    }

    @Test @Config(qualifiers = "+night") fun rendersDarkTheme() {
        compose.setContent { MyApplicationTheme { GiftPackingApp() } }
        compose.onRoot().captureRoboImage("build/ui-check/android-input-dark.png")
    }
}
