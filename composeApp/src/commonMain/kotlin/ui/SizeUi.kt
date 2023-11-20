package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.SizeComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SizeUi(
    sizeComponent: SizeComponent,
    modifier: Modifier = Modifier
) {
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var depth by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Укажи размер подарка",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.width(100.dp),
                singleLine = true,
                placeholder = { Text("Высота") }
            )
            Image(
                painter = painterResource("gift.xml"),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red),
                modifier = Modifier
                    .size(200.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = width,
                onValueChange = { width = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.width(100.dp),
                singleLine = true,
                placeholder = { Text("Ширина") }
            )
            OutlinedTextField(
                value = depth,
                onValueChange = { depth = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.width(100.dp),
                singleLine = true,
                placeholder = { Text("Глубина") }
            )
        }
        Button(
            onClick = {
                sizeComponent.onCalculate(
                    width = width.toDouble(),
                    height = height.toDouble(),
                    depth = depth.toDouble()
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.width(300.dp)
                .height(56.dp)
        ) {
            Text(
                "Рассчитать",
                color = Color.White
            )
        }
    }
}