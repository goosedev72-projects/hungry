package org.gooseprjkt.hungry.data.dao

import androidx.room.*
import org.gooseprjkt.hungry.data.entities.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY updatedDate DESC")
    fun getAllFood(): Flow<List<Food>>
    
    @Query("SELECT * FROM food WHERE id = :id")
    fun getFoodById(id: Long): Flow<Food?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food): Long
    
    @Update
    suspend fun updateFood(food: Food)
    
    @Delete
    suspend fun deleteFood(food: Food)
    
    @Query("DELETE FROM food WHERE id = :id")
    suspend fun deleteFoodById(id: Long)
    
    @Query("SELECT * FROM food WHERE name LIKE '%' || :query || '%'")
    fun searchFood(query: String): Flow<List<Food>>
    
    @Query("SELECT * FROM food WHERE isAvailable = 1")
    fun getAvailableFood(): Flow<List<Food>>
}