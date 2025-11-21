package org.gooseprjkt.hungry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.gooseprjkt.hungry.data.entities.Plan
import org.gooseprjkt.hungry.data.repositories.PlanRepository
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {
    
    private val _plans = planRepository.getAllPlans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val plans: StateFlow<List<Plan>> = _plans
    
    fun getPlansForDate(date: Long) = planRepository.getPlansByDate(date)
    
    fun getPlansForDateRange(startDate: Long, endDate: Long) = 
        planRepository.getPlansBetweenDates(startDate, endDate)
    
    fun insertPlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.insertPlan(plan)
        }
    }
    
    fun updatePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.updatePlan(plan)
        }
    }
    
    fun deletePlan(plan: Plan) {
        viewModelScope.launch {
            planRepository.deletePlan(plan)
        }
    }
    
    fun deletePlanById(id: Long) {
        viewModelScope.launch {
            planRepository.deletePlanById(id)
        }
    }
    
    fun deletePlansForDate(date: Long) {
        viewModelScope.launch {
            planRepository.deletePlansByDate(date)
        }
    }
    
    fun getPlanById(id: Long): Flow<Plan?> {
        return planRepository.getPlanById(id)
    }
}