package com.dsdm.fitro.ui.screens.workout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dsdm.fitro.data.local.AppDatabase
import com.dsdm.fitro.data.local.entity.WorkoutEntity
import com.dsdm.fitro.repository.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepository(
        AppDatabase.getInstance(application).workoutDao()
    )

    val workouts: StateFlow<List<WorkoutEntity>> = repository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addWorkout(name: String, type: String, durationMinutes: Int, exercises: List<String> = emptyList()) {
        viewModelScope.launch {
            repository.insertWorkout(
                WorkoutEntity(
                    name = name,
                    type = type,
                    durationMinutes = durationMinutes,
                    date = LocalDate.now().toString(),
                    exercises = exercises.joinToString(", ")
                )
            )
        }
    }

    fun deleteWorkout(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }
}
