package com.lasalle.triviaLegends.ui.scores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Pantalla de puntuacions
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He utilitzat LazyColumn per mostrar la llista de puntuacions
 * de manera eficient, només renderitzant les que són visibles.
 */
@Composable
fun ScoresScreen(
    viewModel: ScoresViewModel = hiltViewModel()
) {
    val scores by viewModel.scores.collectAsState()
    val bestScore by viewModel.bestScore.collectAsState()
    val worstScore by viewModel.worstScore.collectAsState()
    val averageScore by viewModel.averageScore.collectAsState()
    
    // Estado para la búsqueda
    var searchQuery by remember { mutableStateOf("") }
    
    // Filtrar las puntuaciones según la búsqueda
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
        
        // Barra de búsqueda
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
        
        // Millor i pitjor puntuació
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Millor puntuació
            ElevatedCard(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Millor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    bestScore?.let { score ->
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "${score.score}",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        Text(
                            text = "Encert: ${score.successRate.toInt()}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            
            // Pitjor puntuació
            ElevatedCard(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pitjor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    worstScore?.let { score ->
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "${score.score}",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        
                        Text(
                            text = "Encert: ${score.successRate.toInt()}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
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
                    Divider()
                }
            }
        }
    }
}

/**
 * Element de puntuació individual
 */
@Composable
fun ScoreItem(score: ScoreUiModel) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Puntuación destacada
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${score.score}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Detalles
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = score.formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dificultad
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when(score.difficulty) {
                                "Fàcil" -> Color.Green.copy(alpha = 0.2f)
                                "Mitjana" -> Color.Blue.copy(alpha = 0.2f)
                                "Difícil" -> Color.Red.copy(alpha = 0.2f)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = score.difficulty,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = when(score.difficulty) {
                                "Fàcil" -> Color.Green
                                "Mitjana" -> Color.Blue
                                "Difícil" -> Color.Red
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                    
                    // Aciertos
                    Text(
                        text = "${score.correctAnswers}/${score.totalQuestions}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
} 