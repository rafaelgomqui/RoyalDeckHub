package com.royaldeckhub.app.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.royaldeckhub.app.data.model.Comment
import com.royaldeckhub.app.data.model.Element

object MockRepository {
    // List of Elements
    val elements = listOf(
        Element(1, "Mega Knight", "Lands with the force of 1,000 mustaches.", "Legendary", 7),
        Element(2, "P.E.K.K.A", "A heavily armored, slow melee fighter.", "Epic", 7),
        Element(3, "Electro Wizard", "He lands with a 'Pow!', stuns nearby enemies.", "Legendary", 4),
        Element(4, "The Log", "A spilled bottle of Rage turned an innocent tree trunk into 'The Log'.", "Legendary", 2),
        Element(5, "Hog Rider", "Fast melee troop that targets buildings.", "Special", 4),
        Element(6, "Princess", "Shoots a volley of flaming arrows from long range.", "Legendary", 3),
        Element(7, "Skeleton Army", "Spawns an army of Skeletons.", "Epic", 3),
        Element(8, "Giant", "Slow but durable, only attacks buildings.", "Special", 5),
        Element(9, "Minions", "Three fast, unarmored flying attackers.", "Common", 3),
        Element(10, "Fireball", "Annnnd... Fireball!", "Special", 4)
    )

    // Favorites State (Reactive List of IDs)
    val favoriteIds = mutableStateListOf<Int>()

    // Comments State
    val comments = mutableStateListOf<Comment>(
        Comment(1, 1, "KingBlue", "Too strong currently!", "10 min ago"),
        Comment(2, 5, "HogFan", "Hog Cycle is best.", "1 hour ago")
    )

    fun isFavorite(elementId: Int): Boolean {
        return favoriteIds.contains(elementId)
    }

    fun toggleFavorite(elementId: Int) {
        if (favoriteIds.contains(elementId)) {
            favoriteIds.remove(elementId)
        } else {
            favoriteIds.add(elementId)
        }
    }

    fun addComment(elementId: Int, text: String, author: String = "User") {
        val newId = (comments.maxOfOrNull { it.id } ?: 0) + 1
        comments.add(Comment(newId, elementId, author, text, "Just now"))
    }
    
    fun getCommentsFor(elementId: Int): List<Comment> {
        return comments.filter { it.elementId == elementId }
    }
    
    fun getElement(id: Int): Element? {
        return elements.find { it.id == id }
    }
}
