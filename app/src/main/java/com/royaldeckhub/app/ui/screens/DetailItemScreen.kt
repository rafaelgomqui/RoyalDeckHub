package com.royaldeckhub.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.royaldeckhub.app.data.model.Element
import com.royaldeckhub.app.ui.components.ElixirBadge
import com.royaldeckhub.app.ui.theme.RoyalGold
import androidx.compose.ui.unit.sp

@Composable
fun DetailItemScreen(
    element: Element,
    isFavorite: Boolean,
    onFavClick: (Int) -> Unit,
    isExpanded: Boolean = false
) {
    if (isExpanded) {
        // Landscape / Expanded Layout (Two Pane Style)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Left: Large Image
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("IMG", fontSize = 48.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            
            // Right: Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                DetailContent(element, isFavorite, onFavClick)
            }
        }
    } else {
        // Portrait / Compact Layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("IMG", fontSize = 48.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            DetailContent(element, isFavorite, onFavClick)
        }
    }
}

@Composable
private fun DetailContent(
    element: Element,
    isFavorite: Boolean,
    onFavClick: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = element.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = element.rarity,
            style = MaterialTheme.typography.titleMedium,
            color = RoyalGold,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Cost:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            ElixirBadge(cost = element.elixir)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = element.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { onFavClick(element.id) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
        }
    }
}
