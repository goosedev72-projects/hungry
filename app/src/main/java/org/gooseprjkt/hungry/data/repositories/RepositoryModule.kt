package org.gooseprjkt.hungry.data.repositories

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideRecipeRepository(recipeRepository: RecipeRepository): RecipeRepository {
        return recipeRepository
    }
    
    @Provides
    @Singleton
    fun provideFoodRepository(foodRepository: FoodRepository): FoodRepository {
        return foodRepository
    }
    
    @Provides
    @Singleton
    fun provideGroceryRepository(groceryRepository: GroceryRepository): GroceryRepository {
        return groceryRepository
    }
    
    @Provides
    @Singleton
    fun providePlanRepository(planRepository: PlanRepository): PlanRepository {
        return planRepository
    }
}