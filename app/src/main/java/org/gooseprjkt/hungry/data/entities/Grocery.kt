package org.gooseprjkt.hungry.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery")
data class Grocery(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: String = "",
    val unit: String = "",
    val isPurchased: Boolean = false,
    val priority: String = "normal", // high, normal, low
    val category: String = "",
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis()
)