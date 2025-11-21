package org.gooseprjkt.hungry.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.gooseprjkt.hungry.data.entities.Plan
import org.gooseprjkt.hungry.utils.DateUtils
import org.gooseprjkt.hungry.viewmodels.PlanViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanScreen(
    navController: NavController,
    viewModel: PlanViewModel = hiltViewModel()
) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    val formattedDate = DateUtils.formatDate(selectedDate)
    val plansForDate by viewModel.getPlansForDate(selectedDate).collectAsState(initial = emptyList())
    var showDatePicker by remember { mutableStateOf(false) }
    
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { 
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        selectedDate = selectedDateMillis
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meal Plan") },
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = formattedDate,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select date")
                        }
                    }
                }
            }
            
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Planned Meals", style = MaterialTheme.typography.titleLarge)
                    
                    IconButton(
                        onClick = { /* TODO: Add meal to plan */ }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add meal")
                    }
                }
            }
            
            items(plansForDate) { plan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(plan.mealType.capitalize()) },
                        supportingContent = { 
                            // TODO: Display recipe name (would need to join with recipe table)
                            Text("Recipe planned for this meal")
                        },
                        trailingContent = {
                            IconButton(
                                onClick = { 
                                    // Remove meal from plan
                                    viewModel.deletePlan(plan)
                                }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove")
                            }
                        }
                    )
                }
            }
            
            item {
                if (plansForDate.isEmpty()) {
                    Text(
                        text = "No meals planned for this date",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

fun String.capitalize(): String {
    return if (isNotEmpty()) {
        get(0).uppercaseChar() + substring(1)
    } else {
        this
    }
}