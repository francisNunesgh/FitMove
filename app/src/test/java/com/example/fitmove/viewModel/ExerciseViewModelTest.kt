package com.example.fitmove.viewModel

import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.repository.ExerciseRepository
import com.example.fitmove.domain.usecase.GetExercisesUseCase
import com.example.fitmove.testutil.MainDispatcherRule
import com.example.fitmove.ui.theme.viewModel.ExerciseUiState
import com.example.fitmove.ui.theme.viewModel.ExerciseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeExercise = Exercise(
        id = "1",
        name = "Agachamento",
        primaryMuscle = "quadriceps",
        equipment = listOf("barbell"),
        cues = "Manter as costas estáveis"
    )

    private class FakeExerciseRepository(
        private val exercises: List<Exercise>
    ) : ExerciseRepository {

        override suspend fun getExercises(): List<Exercise> {
            return exercises
        }
    }

    @Test
    fun `when repository succeeds then state is Success`() = runTest (
        mainDispatcherRule.dispatcher) {
        val repository = FakeExerciseRepository(
            listOf(fakeExercise)
        )
        val useCase = GetExercisesUseCase(repository)
        val viewModel = ExerciseViewModel(useCase)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(
            ExerciseUiState.Success(listOf(fakeExercise)),
            state
        )
    }

    @Test
    fun `when repository fails then state is Error`() =
        runTest(mainDispatcherRule.dispatcher) {

            val repository = FailingExerciseRepository()
            val useCase = GetExercisesUseCase(repository)
            val viewModel = ExerciseViewModel(useCase)

            advanceUntilIdle()

            assertEquals(
                ExerciseUiState.Error("Erro ao carregar exercícios"),
                viewModel.uiState.value
            )
        }
}

private class FailingExerciseRepository : ExerciseRepository {
    override suspend fun getExercises(): List<Exercise> {
        throw Exception("Erro ao carregar exercícios")
    }
}
