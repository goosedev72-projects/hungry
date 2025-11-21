package org.gooseprjkt.hungry.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.gooseprjkt.hungry.navigation.HungryScreens
import org.gooseprjkt.hungry.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipes by viewModel.recipes.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipes") },
                actions = {
                    TextButton(onClick = { 
                        navController.navigate("search_recipes")
                    }) {
                        Text("Search")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(HungryScreens.CreateRecipe.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add recipe")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(recipes) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = { 
                        navController.navigate(HungryScreens.RecipeDetail.createRoute(recipe.id))
                    }
                ) {
                    ListItem(
                        headlineContent = { Text(recipe.name) },
                        supportingContent = { Text(recipe.description) }
                    )
                }
            }
        }
    }
}