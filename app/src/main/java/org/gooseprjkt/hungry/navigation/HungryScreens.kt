package org.gooseprjkt.hungry.navigation

sealed class HungryScreens(val route: String) {
    object Recipes : HungryScreens("recipes")
    object RecipeDetail : HungryScreens("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: Long) = "recipe_detail/$recipeId"
    }
    object CreateRecipe : HungryScreens("create_recipe")
    object EditRecipe : HungryScreens("edit_recipe/{recipeId}") {
        fun createRoute(recipeId: Long) = "edit_recipe/$recipeId"
    }
    object CookAlong : HungryScreens("cook_along/{recipeId}") {
        fun createRoute(recipeId: Long) = "cook_along/$recipeId"
    }
    object MealPlan : HungryScreens("meal_plan")
    object Groceries : HungryScreens("groceries")
    object Fridge : HungryScreens("fridge")
    object Settings : HungryScreens("settings")
}