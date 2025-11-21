package org.gooseprjkt.hungry.screens.cookalong

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.gooseprjkt.hungry.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookAlongScreen(
    navController: NavController,
    recipeId: Long,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe by viewModel.getRecipeById(recipeId).collectAsState(initial = null)
    
    var currentStep by remember { mutableIntStateOf(0) }
    var isTimerActive by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(0) }
    
    recipe?.let { currentRecipe ->
        val instructions = currentRecipe.instructions.split("\n").filter { 
            it.trim().isNotEmpty() && !it.startsWith("#") 
        }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cook Along: ${currentRecipe.name}") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (currentStep < instructions.size) {
                    Text(
                        text = "Step ${currentStep + 1} of ${instructions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Text(
                        text = instructions[currentStep],
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    if (isTimerActive) {
                        Text(
                            text = formatTime(timeLeft),
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (currentStep > 0) {
                            OutlinedButton(
                                onClick = { currentStep-- },
                                enabled = !isTimerActive
                            ) {
                                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
                                Text("Previous")
                            }
                        }
                        
                        if (isTimerActive) {
                            Button(
                                onClick = { 
                                    isTimerActive = false
                                    // Stop timer here
                                }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Stop Timer")
                                Text("Stop Timer")
                            }
                        } else {
                            Button(
                                onClick = {
                                    // Start timer if this step has one
                                    val timerPattern = Regex("timer (\\d+)([a-zA-Z]+)")
                                    val match = timerPattern.find(instructions[currentStep])
                                    if (match != null) {
                                        val duration = match.groupValues[1].toIntOrNull()
                                        val unit = match.groupValues[2].lowercase()
                                        
                                        if (duration != null) {
                                            var seconds = duration
                                            when (unit) {
                                                "min", "m" -> seconds *= 60
                                                "sec", "s" -> Unit // Already in seconds
                                                "hr", "h" -> seconds *= 3600
                                            }
                                            timeLeft = seconds
                                            isTimerActive = true
                                            // Start countdown timer here
                                        }
                                    }
                                },
                                enabled = Regex("timer \\d+[a-zA-Z]+").containsMatchIn(instructions[currentStep])
                            ) {
                                Icon(Icons.Default.Timer, contentDescription = "Start Timer")
                                Text("Start Timer")
                            }
                        }
                        
                        if (currentStep < instructions.size - 1) {
                            OutlinedButton(
                                onClick = { 
                                    if (isTimerActive) {
                                        isTimerActive = false
                                    }
                                    currentStep++ 
                                },
                                enabled = !isTimerActive
                            ) {
                                Text("Next")
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
                            }
                        } else {
                            Button(
                                onClick = { 
                                    // Cooking completed
                                    navController.popBackStack()
                                }
                            ) {
                                Text("Finish Cooking")
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Cooking Completed!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    Button(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("Back to Recipe")
                    }
                }
            }
        }
    } ?: run {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Recipe not found")
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}