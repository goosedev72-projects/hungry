package org.gooseprjkt.hungry.data.dao

import androidx.room.*
import org.gooseprjkt.hungry.data.entities.Plan
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Query("SELECT * FROM plan ORDER BY date ASC")
    fun getAllPlans(): Flow<List<Plan>>
    
    @Query("SELECT * FROM plan WHERE id = :id")
    fun getPlanById(id: Long): Flow<Plan?>
    
    @Query("SELECT * FROM plan WHERE date = :date")
    fun getPlansByDate(date: Long): Flow<List<Plan>>
    
    @Query("SELECT * FROM plan WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getPlansBetweenDates(startDate: Long, endDate: Long): Flow<List<Plan>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: Plan): Long
    
    @Update
    suspend fun updatePlan(plan: Plan)
    
    @Delete
    suspend fun deletePlan(plan: Plan)
    
    @Query("DELETE FROM plan WHERE id = :id")
    suspend fun deletePlanById(id: Long)
    
    @Query("DELETE FROM plan WHERE date = :date")
    suspend fun deletePlansByDate(date: Long)
}