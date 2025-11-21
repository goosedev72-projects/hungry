package org.gooseprjkt.hungry.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: String = "",
    val unit: String = "",
    val category: String = "",
    val expiryDate: Long = 0, // timestamp
    val isAvailable: Boolean = true,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis()
)