package org.gooseprjkt.hungry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.gooseprjkt.hungry.data.entities.Food
import org.gooseprjkt.hungry.data.repositories.FoodRepository
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {
    
    private val _fridgeItems = foodRepository.getAvailableFood()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val fridgeItems: StateFlow<List<Food>> = _fridgeItems
    
    fun insertFood(food: Food) {
        viewModelScope.launch {
            foodRepository.insertFood(food)
        }
    }
    
    fun updateFood(food: Food) {
        viewModelScope.launch {
            foodRepository.updateFood(food)
        }
    }
    
    fun deleteFood(food: Food) {
        viewModelScope.launch {
            foodRepository.deleteFood(food)
        }
    }
    
    fun deleteFoodById(id: Long) {
        viewModelScope.launch {
            foodRepository.deleteFoodById(id)
        }
    }
    
    fun searchFood(query: String): Flow<List<Food>> {
        return foodRepository.searchFood(query)
    }
    
    fun getFoodById(id: Long): Flow<Food?> {
        return foodRepository.getFoodById(id)
    }
}