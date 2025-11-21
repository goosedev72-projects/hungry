package org.gooseprjkt.hungry.data.local_room_db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.gooseprjkt.hungry.data.dao.*
import org.gooseprjkt.hungry.data.entities.*
import org.gooseprjkt.hungry.util.Converters

@Database(
    entities = [Recipe::class, Food::class, Grocery::class, Plan::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun foodDao(): FoodDao
    abstract fun groceryDao(): GroceryDao
    abstract fun planDao(): PlanDao
    
    companion object {
        const val DATABASE_NAME = "HungryDB"
    }
}