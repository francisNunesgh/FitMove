package com.example.fitmove.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface ExerciseUiState {
    data object Loading : ExerciseUiState
    data class Success(val exercises: List<Exercise>) : ExerciseUiState

    data class Error(val message: String) : ExerciseUiState
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase

    //private val repository: ExerciseRepository =
    //  ExerciseRepositoryImpl()

) : ViewModel() {
    // private val repository = ExerciseRepository()

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
                val exercises = getExercisesUseCase()
                _uiState.value = ExerciseUiState.Success(exercises)
            } catch (exception: Exception) {
                _uiState.value = ExerciseUiState.Error(
                    exception.message ?: "Não foi possível carregar os exercícios."
                )
            }
        }
    }
}


