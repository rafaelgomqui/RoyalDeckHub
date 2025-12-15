package com.royaldeckhub.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.royaldeckhub.app.ui.theme.RoyalBlueLight

@Composable
fun ElixirBadge(
    cost: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height
            
            // Draw a Drop Shape
            val path = Path().apply {
                moveTo(width / 2, 0f)
                cubicTo(
                    width, height / 3,
                    width, height,
                    width / 2, height
                )
                cubicTo(
                    0f, height,
                    0f, height / 3,
                    width / 2, 0f
                )
                close()
            }
            
            drawPath(path = path, color = RoyalBlueLight) // Using Theme Color
        }
        
        Text(
            text = cost.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}
