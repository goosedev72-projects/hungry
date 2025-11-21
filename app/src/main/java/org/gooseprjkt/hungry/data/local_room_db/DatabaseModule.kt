package org.gooseprjkt.hungry.data.local_room_db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.gooseprjkt.hungry.data.dao.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            Database.DATABASE_NAME
        ).build()
    }
    
    @Provides
    fun provideRecipeDao(database: Database): RecipeDao {
        return database.recipeDao()
    }
    
    @Provides
    fun provideFoodDao(database: Database): FoodDao {
        return database.foodDao()
    }
    
    @Provides
    fun provideGroceryDao(database: Database): GroceryDao {
        return database.groceryDao()
    }
    
    @Provides
    fun providePlanDao(database: Database): PlanDao {
        return database.planDao()
    }
}