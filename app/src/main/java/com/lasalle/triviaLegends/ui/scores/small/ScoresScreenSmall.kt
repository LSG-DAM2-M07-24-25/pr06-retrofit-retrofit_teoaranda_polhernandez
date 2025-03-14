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
import com.lasalle.triviaLegends.ui.scores.ScoreItem
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * Pantalla de puntuacions per a dispositius petits (mòbils)
 * Mostra un disseny vertical amb tota la informació en una columna
 * 
 * @author Pol & Teo
 */
@Composable
fun ScoresScreenSmall(
    viewModel: ScoresViewModel = hiltViewModel()
) {
    val scores by viewModel.scores.collectAsState()
    val bestScore by viewModel.bestScore.collectAsState()
    val worstScore by viewModel.worstScore.collectAsState()
    val averageScore by viewModel.averageScore.collectAsState()
    
    // Estat per a la cerca
    var searchQuery by remember { mutableStateOf("") }
    
    // Filtrar les puntuacions segons la cerca
    val filteredScores = remember(scores, searchQuery) {
        if (searchQuery.isEmpty()) {
            scores
        } else {
            scores.filter { score ->
                score.difficulty.contains(searchQuery, ignoreCase = true) ||
                score.formattedDate.contains(searchQuery, ignoreCase = true) ||
                score.score.toString().contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Puntuacions",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Barra de cerca
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Cerca per data, dificultat o puntuació") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Cerca",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Netejar",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        
        // Estadístiques
        if (scores.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Partides",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${scores.size}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mitjana",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${averageScore.toInt()}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Historial de puntuacions
        Text(
            text = "Historial",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        if (filteredScores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) 
                        "Juga una partida per veure l'historial" 
                    else 
                        "No s'han trobat resultats per '$searchQuery'",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredScores) { score ->
                    ScoreItem(score = score)
                }
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