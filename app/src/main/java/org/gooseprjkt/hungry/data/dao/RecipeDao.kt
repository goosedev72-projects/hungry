package org.gooseprjkt.hungry.data.dao

import androidx.room.*
import org.gooseprjkt.hungry.data.entities.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe ORDER BY updatedDate DESC")
    fun getAllRecipes(): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Long): Flow<Recipe?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long
    
    @Update
    suspend fun updateRecipe(recipe: Recipe)
    
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
    
    @Query("DELETE FROM recipe WHERE id = :id")
    suspend fun deleteRecipeById(id: Long)
    
    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): Flow<List<Recipe>>
}