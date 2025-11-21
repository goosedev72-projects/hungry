package org.gooseprjkt.hungry.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val servings: Int = 1,
    val prepTime: String = "",
    val cookTime: String = "",
    val ingredients: String = "", // JSON string or formatted text
    val instructions: String = "", // JSON string or formatted text
    val tags: String = "", // Comma-separated tags
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis()
)