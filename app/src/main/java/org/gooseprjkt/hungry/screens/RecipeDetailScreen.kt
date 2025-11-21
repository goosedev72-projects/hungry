package org.gooseprjkt.hungry.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Long,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe by viewModel.getRecipeById(recipeId).collectAsState(initial = null)
    
    recipe?.let { currentRecipe ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentRecipe.name) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        text = currentRecipe.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (currentRecipe.servings > 0) {
                        Text(
                            text = "Servings: ${currentRecipe.servings}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Ingredients section
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Parse and display ingredients
                    currentRecipe.ingredients.split("\n").forEach { ingredient ->
                        if (ingredient.trim().isNotEmpty()) {
                            if (ingredient.startsWith("#")) {
                                // This is a group title
                                Text(
                                    text = ingredient.substring(1).trim(),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            } else {
                                // This is an ingredient
                                Text(
                                    text = "• $ingredient",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Instructions section
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Parse and display instructions
                    currentRecipe.instructions.split("\n").forEachIndexed { index, instruction ->
                        if (instruction.trim().isNotEmpty()) {
                            if (instruction.startsWith("#")) {
                                // This is a group title
                                Text(
                                    text = instruction.substring(1).trim(),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            } else {
                                // This is an instruction
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${index + 1}. ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.width(30.dp)
                                    )
                                    if (instruction.contains("timer")) {
                                        // Extract timer from instruction
                                        val timerPattern = Regex("timer (\\d+\\w+)")
                                        val match = timerPattern.find(instruction)
                                        if (match != null) {
                                            val timerText = match.groupValues[1]
                                            val instructionText = instruction.replace(timerPattern, "")
                                            Column {
                                                Text(
                                                    text = instructionText.trim(),
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = "Timer: $timerText",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        } else {
                                            Text(
                                                text = instruction,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = instruction,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Cook Along button
                    Button(
                        onClick = { 
                            // Navigate to cook along screen
                            navController.navigate("${HungryScreens.CookAlong.route}/$recipeId")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Cook Along")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Edit button
                    OutlinedButton(
                        onClick = { 
                            // Navigate to edit recipe screen
                            navController.navigate("${HungryScreens.EditRecipe.route}/$recipeId")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Edit Recipe")
                    }
                }
            }
        }
    } ?: run {
        // Recipe not found
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Recipe not found")
        }
    }
}