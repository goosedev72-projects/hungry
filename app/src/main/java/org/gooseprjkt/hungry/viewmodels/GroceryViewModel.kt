package org.gooseprjkt.hungry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.gooseprjkt.hungry.data.entities.Grocery
import org.gooseprjkt.hungry.data.repositories.GroceryRepository
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val groceryRepository: GroceryRepository
) : ViewModel() {
    
    private val _groceries = groceryRepository.getUnpurchasedGroceries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val groceries: StateFlow<List<Grocery>> = _groceries
    
    fun insertGrocery(grocery: Grocery) {
        viewModelScope.launch {
            groceryRepository.insertGrocery(grocery)
        }
    }
    
    fun updateGrocery(grocery: Grocery) {
        viewModelScope.launch {
            groceryRepository.updateGrocery(grocery)
        }
    }
    
    fun deleteGrocery(grocery: Grocery) {
        viewModelScope.launch {
            groceryRepository.deleteGrocery(grocery)
        }
    }
    
    fun deleteGroceryById(id: Long) {
        viewModelScope.launch {
            groceryRepository.deleteGroceryById(id)
        }
    }
    
    fun toggleGroceryPurchasedStatus(id: Long, purchased: Boolean) {
        viewModelScope.launch {
            groceryRepository.updateGroceryPurchasedStatus(id, purchased)
        }
    }
    
    fun getGroceryById(id: Long): Flow<Grocery?> {
        return groceryRepository.getGroceryById(id)
    }
}