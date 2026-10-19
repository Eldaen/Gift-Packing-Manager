package com.example.giftpackinghelper.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftpackinghelper.Calculator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyApplicationTheme { GiftPackingApp() } }
    }
}

@Composable
fun GiftPackingApp() {
    var height by rememberSaveable { mutableStateOf("") }
    var width by rememberSaveable { mutableStateOf("") }
    var depth by rememberSaveable { mutableStateOf("") }
    var showResult by rememberSaveable { mutableStateOf(false) }
    val calculator = remember { Calculator() }
    val result = calculator.calculateOrNull(width, height, depth)
    val focus = LocalFocusManager.current
    BackHandler(enabled = showResult) { showResult = false }

    Surface(color = MaterialTheme.colorScheme.background) {
        ScreenContainer {
            if (showResult && result != null) {
                ResultScreen(result) {
                    showResult = false
                    height = ""
                    width = ""
                    depth = ""
                }
            } else {
                SizesScreen(
                    height, width, depth,
                    onHeight = { height = it },
                    onWidth = { width = it },
                    onDepth = { depth = it },
                    canCalculate = result != null,
                    onCalculate = {
                        focus.clearFocus()
                        showResult = true
                    },
                )
            }
        }
    }
}

@Composable
private fun ScreenContainer(content: @Composable ColumnScope.() -> Unit) {
    BoxWithConstraints(
        Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing).imePadding(),
    ) {
        Column(
            Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                Modifier.widthIn(max = 348.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content,
            )
        }
    }
}

@Composable
private fun SizesScreen(
    height: String, width: String, depth: String,
    onHeight: (String) -> Unit, onWidth: (String) -> Unit, onDepth: (String) -> Unit,
    canCalculate: Boolean, onCalculate: () -> Unit,
) {
    ScreenTitle("Укажи размер подарка")
    Spacer(Modifier.height(28.dp))
    Image(
        painterResource(R.drawable.gift_icon), contentDescription = null,
        modifier = Modifier.size(200.dp),
        colorFilter = ColorFilter.tint(GiftRed),
    )
    Spacer(Modifier.height(28.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        DimensionField("Высота", height, onHeight, Modifier.weight(1f))
        DimensionField("Ширина", width, onWidth, Modifier.weight(1f))
        DimensionField("Глубина", depth, onDepth, Modifier.weight(1f))
    }
    Spacer(Modifier.height(16.dp))
    Caption("Размеры в сантиметрах")
    Spacer(Modifier.height(48.dp))
    GiftButton("Рассчитать", canCalculate, onCalculate)
}

@Composable
private fun DimensionField(label: String, value: String, onChange: (String) -> Unit, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Caption(label)
        BasicTextField(
            value = value, onValueChange = onChange, singleLine = true,
            cursorBrush = SolidColor(GiftRed),
            textStyle = TextStyle(fontSize = 17.sp, color = MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().height(52.dp)
                .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .semantics { contentDescription = label },
            decorationBox = { inner ->
                Box(Modifier.padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) Text("0", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 17.sp)
                    inner()
                }
            },
        )
    }
}

@Composable
private fun ResultScreen(size: Calculator.PaperSize, onNewGift: () -> Unit) {
    ScreenTitle("Вот нужные размеры")
    Spacer(Modifier.height(16.dp))
    Caption("Размер бумаги в сантиметрах")
    Spacer(Modifier.height(34.dp))
    Column(
        Modifier.width(240.dp).border(2.dp, GiftRed, RoundedCornerShape(16.dp)).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ResultRow("Высота", size.height)
        ResultRow("Ширина", size.width)
    }
    Spacer(Modifier.height(50.dp))
    GiftButton("Новый подарок", onClick = onNewGift)
}

@Composable
private fun ResultRow(label: String, value: Double) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 17.sp)
        Text(String.format(LocalLocale.current.platformLocale, "%.1f", value), fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ScreenTitle(text: String) {
    Text(text, fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
}

@Composable
private fun Caption(text: String) {
    Text(text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun GiftButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick, enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GiftRed, contentColor = androidx.compose.ui.graphics.Color.White,
            disabledContainerColor = GiftRed.copy(alpha = 0.45f),
            disabledContentColor = androidx.compose.ui.graphics.Color.White,
        ),
    ) { Text(text, fontSize = 17.sp, fontWeight = FontWeight.SemiBold) }
}

@Preview(showBackground = true, locale = "ru", widthDp = 402, heightDp = 874)
@Composable
fun InputPreview() {
    MyApplicationTheme { GiftPackingApp() }
}

@Preview(showBackground = true, locale = "ru", widthDp = 402, heightDp = 874)
@Composable
fun ResultPreview() {
    MyApplicationTheme { Surface { ScreenContainer { ResultScreen(Calculator.PaperSize(33.0, 26.6667)) {} } } }
}
