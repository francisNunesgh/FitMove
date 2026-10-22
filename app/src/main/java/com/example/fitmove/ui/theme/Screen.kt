package com.example.fitmove.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitmove.ui.theme.viewModel.ExerciseUiState
import com.example.fitmove.ui.theme.viewModel.ExerciseViewModel

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Exercícios FitMove",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            when (val state = uiState) {
                is ExerciseUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "A carregar exercícios...",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is ExerciseUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.exercises,
                            key = { it.id }
                        ) { exercise ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "Grupo muscular: ${exercise.primaryMuscle}"
                                    )

                                    Text(
                                        text = "Equipamento: ${
                                            exercise.equipment.joinToString(", ")
                                                .ifBlank { "Não especificado" }
                                        }"
                                    )

                                    Text(
                                        text = "Orientações: ${exercise.cues}"
                                    )
                                }
                            }
                        }
                    }
                }

                is ExerciseUiState.Error -> {
                    Text(
                        text = "Erro: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
