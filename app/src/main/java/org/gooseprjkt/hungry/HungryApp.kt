package org.gooseprjkt.hungry

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.gooseprjkt.hungry.navigation.HungryScreens
import org.gooseprjkt.hungry.screens.*
import org.gooseprjkt.hungry.screens.cookalong.CookAlongScreen
import org.gooseprjkt.hungry.screens.search.SearchRecipesScreen

@Composable
fun HungryApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = HungryScreens.Recipes.route
    ) {
        composable(HungryScreens.Recipes.route) {
            RecipesScreen(navController)
        }
        composable(
            route = HungryScreens.RecipeDetail.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: -1L
            RecipeDetailScreen(navController, recipeId)
        }
        composable(
            route = HungryScreens.EditRecipe.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: -1L
            CreateRecipeScreen(navController, recipeId)
        }
        composable(HungryScreens.CreateRecipe.route) {
            CreateRecipeScreen(navController)
        }
        composable(
            route = HungryScreens.CookAlong.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: -1L
            CookAlongScreen(navController, recipeId)
        }
        composable(HungryScreens.MealPlan.route) {
            MealPlanScreen(navController)
        }
        composable(HungryScreens.Groceries.route) {
            GroceriesScreen(navController)
        }
        composable(HungryScreens.Fridge.route) {
            FridgeScreen(navController)
        }
        composable(HungryScreens.Settings.route) {
            SettingsScreen(navController)
        }
        composable("search_recipes") {
            SearchRecipesScreen(navController)
        }
    }
}