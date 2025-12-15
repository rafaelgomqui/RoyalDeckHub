package com.royaldeckhub.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.royaldeckhub.app.data.model.Element
import com.royaldeckhub.app.ui.components.ElementCard

@Composable
fun ElemListScreen(
    elements: List<Element>,
    favoriteIds: List<Int>,
    onElementClick: (Int) -> Unit,
    onFavClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    // Adaptive Layout Parameter
    isExpanded: Boolean = false
) {
    // Determine grid columns based on expansion state
    val columns = if (isExpanded) GridCells.Fixed(3) else GridCells.Fixed(2)

    LazyVerticalGrid(
        columns = columns,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(elements) { element ->
            ElementCard(
                element = element,
                isFavorite = favoriteIds.contains(element.id),
                onElementClick = onElementClick,
                onFavClick = onFavClick
            )
        }
    }
}

