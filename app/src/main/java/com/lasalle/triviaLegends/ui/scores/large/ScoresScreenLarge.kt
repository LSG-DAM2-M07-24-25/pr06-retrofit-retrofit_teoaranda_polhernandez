package com.lasalle.triviaLegends.ui.scores.large

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.scores.ScoresViewModel
import com.lasalle.triviaLegends.ui.scores.ScoreUiModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

@Composable
fun ScoresScreenLarge(
    viewModel: ScoresViewModel = hiltViewModel()
) {
    val scores by viewModel.scores.collectAsState()
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Panel lateral izquierdo (20%)
        Card(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
                .padding(end = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Estadístiques",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                val totalGames = scores.size
                val totalScore = scores.sumOf { it.score }
                val avgScore = if (totalGames > 0) totalScore / totalGames else 0
                
                StatCard(
                    title = "Total partides",
                    value = totalGames.toString()
                )
                
                StatCard(
                    title = "Puntuació mitjana",
                    value = avgScore.toString()
                )
            }
        }
        
        // Contenido principal (80%)
        Column(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Historial de Puntuacions",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(scores) { score ->
                    ScoreCard(score = score)
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun ScoreCard(score: ScoreUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Puntuació",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "${score.score}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Respostes correctes: ${score.correctAnswers}/${score.totalQuestions}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Data: ${score.formattedDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 