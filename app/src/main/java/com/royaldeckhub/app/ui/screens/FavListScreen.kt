package com.royaldeckhub.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.royaldeckhub.app.data.model.Element

@Composable
fun FavListScreen(
    elements: List<Element>,
    favoriteIds: List<Int>,
    onElementClick: (Int) -> Unit,
    onRemoveFav: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    val favElements = elements.filter { favoriteIds.contains(it.id) }

    if (favElements.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorites yet!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        ElemListScreen(
            elements = favElements,
            favoriteIds = favoriteIds,
            onElementClick = onElementClick,
            onFavClick = onRemoveFav, // Reusing logic (FavClick in this context removes)
            modifier = modifier,
            isExpanded = isExpanded
        )
    }
}
