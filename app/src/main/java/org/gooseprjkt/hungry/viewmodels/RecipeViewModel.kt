package org.gooseprjkt.hungry.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.gooseprjkt.hungry.data.entities.Recipe
import org.gooseprjkt.hungry.data.repositories.RecipeRepository
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    
    private val _recipes = recipeRepository.getAllRecipes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val recipes: StateFlow<List<Recipe>> = _recipes
    
    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.insertRecipe(recipe)
        }
    }
    
    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.updateRecipe(recipe)
        }
    }
    
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }
    
    fun deleteRecipeById(id: Long) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeById(id)
        }
    }
    
    fun searchRecipes(query: String): Flow<List<Recipe>> {
        return recipeRepository.searchRecipes(query)
    }
    
    fun getRecipeById(id: Long): Flow<Recipe?> {
        return recipeRepository.getRecipeById(id)
    }
}