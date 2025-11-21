package org.gooseprjkt.hungry.data.dao

import androidx.room.*
import org.gooseprjkt.hungry.data.entities.Grocery
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Query("SELECT * FROM grocery ORDER BY updatedDate DESC")
    fun getAllGroceries(): Flow<List<Grocery>>
    
    @Query("SELECT * FROM grocery WHERE id = :id")
    fun getGroceryById(id: Long): Flow<Grocery?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrocery(grocery: Grocery): Long
    
    @Update
    suspend fun updateGrocery(grocery: Grocery)
    
    @Delete
    suspend fun deleteGrocery(grocery: Grocery)
    
    @Query("DELETE FROM grocery WHERE id = :id")
    suspend fun deleteGroceryById(id: Long)
    
    @Query("SELECT * FROM grocery WHERE isPurchased = 0 ORDER BY priority DESC")
    fun getUnpurchasedGroceries(): Flow<List<Grocery>>
    
    @Query("SELECT * FROM grocery WHERE isPurchased = 1 ORDER BY updatedDate DESC")
    fun getPurchasedGroceries(): Flow<List<Grocery>>
    
    @Query("UPDATE grocery SET isPurchased = :purchased WHERE id = :id")
    suspend fun updateGroceryPurchasedStatus(id: Long, purchased: Boolean)
}