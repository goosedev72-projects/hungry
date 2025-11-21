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
import org.gooseprjkt.hungry.viewmodels.GroceryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceriesScreen(
    navController: NavController,
    viewModel: GroceryViewModel = hiltViewModel()
) {
    val groceries by viewModel.groceries.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Groceries") },
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
            items(groceries) { grocery ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(grocery.name) },
                        supportingContent = { 
                            if (grocery.amount.isNotEmpty()) {
                                Text("${grocery.amount} ${grocery.unit}")
                            }
                        },
                        trailingContent = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(onClick = { 
                                    viewModel.toggleGroceryPurchasedStatus(grocery.id, true)
                                }) {
                                    Icon(Icons.Default.Check, contentDescription = "Mark as purchased")
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