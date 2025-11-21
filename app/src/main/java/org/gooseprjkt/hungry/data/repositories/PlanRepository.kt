package org.gooseprjkt.hungry.data.repositories

import kotlinx.coroutines.flow.Flow
import org.gooseprjkt.hungry.data.dao.PlanDao
import org.gooseprjkt.hungry.data.entities.Plan
import javax.inject.Inject

class PlanRepository @Inject constructor(
    private val planDao: PlanDao
) {
    fun getAllPlans(): Flow<List<Plan>> = planDao.getAllPlans()
    
    fun getPlanById(id: Long): Flow<Plan?> = planDao.getPlanById(id)
    
    fun getPlansByDate(date: Long): Flow<List<Plan>> = planDao.getPlansByDate(date)
    
    fun getPlansBetweenDates(startDate: Long, endDate: Long): Flow<List<Plan>> = 
        planDao.getPlansBetweenDates(startDate, endDate)
    
    suspend fun insertPlan(plan: Plan): Long = planDao.insertPlan(plan)
    
    suspend fun updatePlan(plan: Plan) = planDao.updatePlan(plan)
    
    suspend fun deletePlan(plan: Plan) = planDao.deletePlan(plan)
    
    suspend fun deletePlanById(id: Long) = planDao.deletePlanById(id)
    
    suspend fun deletePlansByDate(date: Long) = planDao.deletePlansByDate(date)
}