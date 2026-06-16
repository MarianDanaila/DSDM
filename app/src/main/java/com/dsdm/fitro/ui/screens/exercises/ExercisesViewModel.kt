package com.dsdm.fitro.ui.screens.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsdm.fitro.data.remote.api.RetrofitClient
import com.dsdm.fitro.data.remote.model.ExerciseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ExercisesUiState {
    object Loading : ExercisesUiState()
    data class Success(val exercises: List<ExerciseResponse>) : ExercisesUiState()
    data class Error(val message: String) : ExercisesUiState()
}

class ExercisesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ExercisesUiState>(ExercisesUiState.Loading)
    val uiState: StateFlow<ExercisesUiState> = _uiState

    init {
        fetchExercises()
    }

    fun fetchExercises() {
        viewModelScope.launch {
            _uiState.value = ExercisesUiState.Loading
            try {
                val response = RetrofitClient.exerciseApi.getExercises(limit = 20)
                val validExercises = response.results.filter { it.getEnglishName() != null }
                _uiState.value = ExercisesUiState.Success(validExercises)
            } catch (e: Exception) {
                _uiState.value = ExercisesUiState.Error("Failed to load exercises. Check your connection.")
            }
        }
    }
}
