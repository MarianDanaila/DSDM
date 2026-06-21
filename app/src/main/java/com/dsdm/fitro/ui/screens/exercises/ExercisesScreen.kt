package com.dsdm.fitro.ui.screens.exercises

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dsdm.fitro.data.remote.model.ExerciseResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(navController: NavController) {
    val viewModel: ExercisesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val categoryCount by viewModel.categoryCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercises") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ExercisesUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ExercisesUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchExercises() }) {
                            Text("Retry")
                        }
                    }
                }
                is ExercisesUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (categoryCount > 0) {
                            item {
                                Text(
                                    text = "$categoryCount categories available",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = androidx.compose.ui.Modifier.padding(bottom = 4.dp)
                                )
                            }
                        }
                        items(state.exercises) { exercise ->
                            ExerciseCard(exercise)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(exercise: ExerciseResponse) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exercise.getEnglishName() ?: "Unknown",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = exercise.category?.name ?: "Uncategorized",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (!exercise.muscles.isNullOrEmpty()) {
                Text(
                    text = "Muscles: ${exercise.muscles.joinToString { it.nameEn }}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
