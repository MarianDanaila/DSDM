package com.dsdm.fitro.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dsdm.fitro.ui.screens.exercises.ExercisesUiState
import com.dsdm.fitro.ui.screens.exercises.ExercisesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(navController: NavController, viewModel: WorkoutViewModel) {
    val exercisesViewModel: ExercisesViewModel = viewModel()
    val exercisesUiState by exercisesViewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val selectedExercises = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Workout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Workout Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Type (e.g. Cardio, Strength)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (minutes)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Select Exercises",
                style = MaterialTheme.typography.titleMedium
            )

            when (val state = exercisesUiState) {
                is ExercisesUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ExercisesUiState.Error -> {
                    Text(
                        text = "Could not load exercises.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is ExercisesUiState.Success -> {
                    state.exercises.forEach { exercise ->
                        val exerciseName = exercise.getEnglishName() ?: return@forEach
                        val isSelected = selectedExercises.contains(exerciseName)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    if (isSelected) selectedExercises.remove(exerciseName)
                                    else selectedExercises.add(exerciseName)
                                }
                            )
                            Text(text = exerciseName)
                        }
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    when {
                        name.isBlank() || type.isBlank() || duration.isBlank() -> {
                            errorMessage = "Please fill in all fields."
                        }
                        duration.toIntOrNull() == null -> {
                            errorMessage = "Duration must be a number."
                        }
                        else -> {
                            viewModel.addWorkout(name, type, duration.toInt(), selectedExercises.toList())
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Workout")
            }
        }
    }
}
