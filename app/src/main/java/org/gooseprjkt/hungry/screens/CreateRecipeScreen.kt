package org.gooseprjkt.hungry.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.gooseprjkt.hungry.data.entities.Recipe
import org.gooseprjkt.hungry.navigation.HungryScreens
import org.gooseprjkt.hungry.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    navController: NavController,
    recipeId: Long = -1L, // -1 means creating new, otherwise editing
    viewModel: RecipeViewModel = hiltViewModel()
) {
    var recipeName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("1") }
    var isLoading by remember { mutableStateOf(false) }
    
    // Load recipe if we're editing
    LaunchedEffect(recipeId) {
        if (recipeId != -1L) {
            viewModel.getRecipeById(recipeId).collect { recipe ->
                if (recipe != null) {
                    recipeName = recipe.name
                    ingredients = recipe.ingredients
                    instructions = recipe.instructions
                    servings = recipe.servings.toString()
                }
            }
        }
    }
    
    val isEditing = recipeId != -1L
    val title = if (isEditing) "Edit Recipe" else "Create Recipe"
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (recipeName.isNotBlank() && (ingredients.isNotBlank() || instructions.isNotBlank())) {
                                isLoading = true
                                val recipe = Recipe(
                                    id = if (isEditing) recipeId else 0,
                                    name = recipeName,
                                    ingredients = ingredients,
                                    instructions = instructions,
                                    servings = servings.toIntOrNull() ?: 1
                                )
                                if (isEditing) {
                                    viewModel.updateRecipe(recipe)
                                } else {
                                    viewModel.insertRecipe(recipe)
                                }
                                isLoading = false
                                navController.popBackStack()
                            }
                        },
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Check, contentDescription = "Save")
                        }
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
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = { recipeName = it },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = servings,
                    onValueChange = { 
                        // Only allow numbers
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            servings = it 
                        }
                    },
                    label = { Text("Servings") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredients") },
                    placeholder = { Text("Paste ingredients or enter manually") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    supportingText = {
                        Text("Format: amount unit ingredient (e.g. 300g apples)\nUse #Group name for grouping")
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Instructions") },
                    placeholder = { Text("Paste instructions or enter manually") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    supportingText = {
                        Text("Format: instruction with timer 6min\nUse #Group name for grouping")
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            ingredients = "#Paste ingredients example\n300g apples\n2 tbsp sugar\n1 lemon"
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Paste Ingredients")
                    }
                    
                    Button(
                        onClick = {
                            instructions = "#Paste instructions example\nPeel and slice apples\n\nCook with sugar for 6min\nAdd lemon juice"
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Paste Instructions")
                    }
                }
            }
        }
    }
}