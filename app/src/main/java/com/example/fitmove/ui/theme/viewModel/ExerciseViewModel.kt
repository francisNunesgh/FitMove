package com.example.fitmove.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitmove.data.remote.ExerciseDto
import com.example.fitmove.data.remote.RetrofitClient
import com.example.fitmove.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ExerciseUiState {
    data object Loading : ExerciseUiState
    data class Success(val exercises: List<ExerciseDto>) : ExerciseUiState
    data class Error(val message: String) : ExerciseUiState
}

class ExerciseViewModel : ViewModel() {
    private val repository = ExerciseRepository()

    private val _uiState =
        MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)

    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            _uiState.value = ExerciseUiState.Loading

            try {
                val response = RetrofitClient.exerciseApi.getExercises()
                _uiState.value = ExerciseUiState.Success(response.data)
            } catch (exception: Exception) {
                _uiState.value = ExerciseUiState.Error(
                    exception.message ?: "Não foi possível carregar os exercícios."
                )
            }
        }
    }
}
