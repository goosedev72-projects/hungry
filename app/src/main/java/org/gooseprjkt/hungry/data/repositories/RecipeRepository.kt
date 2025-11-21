package org.gooseprjkt.hungry.data.repositories

import kotlinx.coroutines.flow.Flow
import org.gooseprjkt.hungry.data.dao.RecipeDao
import org.gooseprjkt.hungry.data.entities.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()
    
    fun getRecipeById(id: Long): Flow<Recipe?> = recipeDao.getRecipeById(id)
    
    suspend fun insertRecipe(recipe: Recipe): Long = recipeDao.insertRecipe(recipe)
    
    suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe)
    
    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)
    
    suspend fun deleteRecipeById(id: Long) = recipeDao.deleteRecipeById(id)
    
    fun searchRecipes(query: String): Flow<List<Recipe>> = recipeDao.searchRecipes(query)
}