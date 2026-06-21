package com.dsdm.fitro.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.dsdm.fitro.data.local.entity.WorkoutEntity
import com.dsdm.fitro.ui.screens.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(navController: NavController) {
    val viewModel: WorkoutViewModel = viewModel()
    val workouts by viewModel.workouts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Workouts") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_WORKOUT) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Workout")
            }
        }
    ) { padding ->
        if (workouts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No workouts yet. Tap + to add one!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutCard(workout = workout, onDelete = { viewModel.deleteWorkout(workout) })
                }
            }
        }
    }
}

@Composable
fun WorkoutCard(workout: WorkoutEntity, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = workout.name, fontWeight = FontWeight.Bold)
                Text(text = workout.type, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "${workout.durationMinutes} min · ${workout.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (workout.exercises.isNotBlank()) {
                    Text(
                        text = "Exercises: ${workout.exercises}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
