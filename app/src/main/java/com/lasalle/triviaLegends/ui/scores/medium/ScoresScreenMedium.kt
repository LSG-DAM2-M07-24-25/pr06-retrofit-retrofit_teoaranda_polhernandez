package com.lasalle.triviaLegends.ui.scores.medium

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
import com.lasalle.triviaLegends.ui.scores.StatCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * Pantalla de puntuacions per a dispositius mitjans (tablets petites, mòbils en horitzontal)
 * Mostra un disseny amb un panell lateral esquerre i el contingut principal a la dreta
 * 
 * @author Pol & Teo
 */
@Composable
fun ScoresScreenMedium(
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
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Panell lateral amb estadístiques (30% de l'amplada)
        Card(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .padding(end = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
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
        
        // Contingut principal (70% de l'amplada)
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Historial de Puntuacions",
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
} 