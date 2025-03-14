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
import com.lasalle.triviaLegends.ui.scores.ScoreCard
import com.lasalle.triviaLegends.ui.scores.StatCard
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * Pantalla de puntuacions per a dispositius grans (tablets, ordinadors)
 * Mostra un disseny amb un panell lateral esquerre i una graella de puntuacions
 * 
 * @author Pol & Teo
 */
@Composable
fun ScoresScreenLarge(
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
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Panell lateral esquerre (20%)
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
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
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
                
                // Millor i pitjor puntuació
                bestScore?.let { score ->
                    StatCard(
                        title = "Millor puntuació",
                        value = score.score.toString()
                    )
                }
                
                worstScore?.let { score ->
                    StatCard(
                        title = "Pitjor puntuació",
                        value = score.score.toString()
                    )
                }
            }
        }
        
        // Contingut principal (80%)
        Column(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Historial de Puntuacions",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Barra de cerca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredScores) { score ->
                        ScoreCard(score = score)
                    }
                }
            }
        }
    }
} 