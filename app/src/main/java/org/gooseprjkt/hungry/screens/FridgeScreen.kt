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
import org.gooseprjkt.hungry.viewmodels.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgeScreen(
    navController: NavController,
    viewModel: FoodViewModel = hiltViewModel()
) {
    val fridgeItems by viewModel.fridgeItems.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fridge") },
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
            items(fridgeItems) { food ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(food.name) },
                        supportingContent = { 
                            if (food.amount.isNotEmpty()) {
                                Text("${food.amount} ${food.unit}")
                            }
                        },
                        trailingContent = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(onClick = { /* TODO: Use item in recipe */ }) {
                                    Icon(Icons.Default.Check, contentDescription = "Use")
                                }
                                IconButton(onClick = { /* TODO: Remove item */ }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}