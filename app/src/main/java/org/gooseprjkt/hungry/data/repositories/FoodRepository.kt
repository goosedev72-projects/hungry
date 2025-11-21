package org.gooseprjkt.hungry.data.repositories

import kotlinx.coroutines.flow.Flow
import org.gooseprjkt.hungry.data.dao.FoodDao
import org.gooseprjkt.hungry.data.entities.Food
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodDao: FoodDao
) {
    fun getAllFood(): Flow<List<Food>> = foodDao.getAllFood()
    
    fun getFoodById(id: Long): Flow<Food?> = foodDao.getFoodById(id)
    
    suspend fun insertFood(food: Food): Long = foodDao.insertFood(food)
    
    suspend fun updateFood(food: Food) = foodDao.updateFood(food)
    
    suspend fun deleteFood(food: Food) = foodDao.deleteFood(food)
    
    suspend fun deleteFoodById(id: Long) = foodDao.deleteFoodById(id)
    
    fun searchFood(query: String): Flow<List<Food>> = foodDao.searchFood(query)
    
    fun getAvailableFood(): Flow<List<Food>> = foodDao.getAvailableFood()
}