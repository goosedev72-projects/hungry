package org.gooseprjkt.hungry.data.repositories

import kotlinx.coroutines.flow.Flow
import org.gooseprjkt.hungry.data.dao.GroceryDao
import org.gooseprjkt.hungry.data.entities.Grocery
import javax.inject.Inject

class GroceryRepository @Inject constructor(
    private val groceryDao: GroceryDao
) {
    fun getAllGroceries(): Flow<List<Grocery>> = groceryDao.getAllGroceries()
    
    fun getGroceryById(id: Long): Flow<Grocery?> = groceryDao.getGroceryById(id)
    
    suspend fun insertGrocery(grocery: Grocery): Long = groceryDao.insertGrocery(grocery)
    
    suspend fun updateGrocery(grocery: Grocery) = groceryDao.updateGrocery(grocery)
    
    suspend fun deleteGrocery(grocery: Grocery) = groceryDao.deleteGrocery(grocery)
    
    suspend fun deleteGroceryById(id: Long) = groceryDao.deleteGroceryById(id)
    
    fun getUnpurchasedGroceries(): Flow<List<Grocery>> = groceryDao.getUnpurchasedGroceries()
    
    fun getPurchasedGroceries(): Flow<List<Grocery>> = groceryDao.getPurchasedGroceries()
    
    suspend fun updateGroceryPurchasedStatus(id: Long, purchased: Boolean) = 
        groceryDao.updateGroceryPurchasedStatus(id, purchased)
}