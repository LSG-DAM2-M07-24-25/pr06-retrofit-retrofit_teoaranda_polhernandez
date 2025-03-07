package com.lasalle.triviaLegends.ui.scores.small

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.scores.ScoresViewModel
import com.lasalle.triviaLegends.ui.scores.ScoreUiModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun ScoresScreenSmall(
    viewModel: ScoresViewModel = hiltViewModel()
) {
    val scores by viewModel.scores.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Puntuacions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(scores) { score ->
                ScoreCard(score = score)
            }
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
                .padding(16.dp)
        ) {
            Text(
                text = "Puntuació: ${score.score}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Respostes correctes: ${score.correctAnswers}/${score.totalQuestions}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${score.formattedDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 