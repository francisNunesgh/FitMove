package com.example.fitmove.viewModel

import com.example.fitmove.data.remote.ExerciseDto
import com.example.fitmove.data.repository.ExerciseRepository
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

    private val fakeExercise = ExerciseDto(
        id = "1",
        name = "Agachamento",
        primaryMuscle = "quadriceps",
        equipment = listOf("barbell"),
        cues = "Manter as costas estáveis"
    )

    private class FakeExerciseRepository(
        private val exercises: List<ExerciseDto>
    ) : ExerciseRepository {

        override suspend fun getExercises(): List<ExerciseDto> {
            return exercises
        }
    }

    @Test
    fun `when repository succeeds then state is Success`() = runTest (
        (mainDispatcherRule.dispatcher)) {
        // Arrange: preparar os dados de teste
        val repository = FakeExerciseRepository(
            listOf(fakeExercise)
        )
        val viewModel = ExerciseViewModel(repository)


        // Act: executar as corrotinas pendentes
        advanceUntilIdle()

        // Assert: verificar o resultado
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
            val viewModel = ExerciseViewModel(repository)

            advanceUntilIdle()

            assertEquals(
                ExerciseUiState.Error("Erro ao carregar exercícios"),
                viewModel.uiState.value
            )
        }
}

private class FailingExerciseRepository : ExerciseRepository {
    override suspend fun getExercises(): List<ExerciseDto> {
        throw Exception("Erro ao carregar exercícios")
    }
}
