package com.lasalle.triviaLegends.ui.settings.medium

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.settings.InfoViewModel

@Composable
fun InfoScreenMedium(
    viewModel: InfoViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Panel lateral (30%)
        Card(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .padding(end = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Trivia Legends",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Versió 1.0.0",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        
        // Contenido principal (70%)
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
        ) {
            Text(
                text = "Informació",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Sobre l'aplicació",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Trivia Legends és un joc de preguntes i respostes desenvolupat per estudiants de La Salle. Aquest projecte forma part de l'assignatura de Desenvolupament d'Aplicacions Mòbils.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Desenvolupadors",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DeveloperCard("Pol Hernández")
                        DeveloperCard("Teo Aranda")
                    }
                }
            }
        }
    }
}

@Composable
private fun DeveloperCard(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
} 