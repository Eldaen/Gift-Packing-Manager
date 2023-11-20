package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import component.ResultComponent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ResultUi(
    resultComponent: ResultComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Вот нужные размеры",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.padding(bottom = 16.dp),
            border = BorderStroke(1.dp, Color.Red)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    Text("Высота: ")
                    Text(resultComponent.paperSize.height.toString())
                }
                Row {
                    Text("Ширина: ")
                    Text(resultComponent.paperSize.width.toString())
                } 
            }
        }
        Button(
            onClick = { resultComponent.onNewGift() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.width(300.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Новый подарок",
                color = Color.White
            )
        }
    }
}