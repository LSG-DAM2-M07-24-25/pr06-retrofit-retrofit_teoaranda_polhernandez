package com.lasalle.triviaLegends.ui.settings.large

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.settings.InfoViewModel

@Composable
fun InfoScreenLarge(
    viewModel: InfoViewModel = hiltViewModel()
) {
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
                .padding(end = 24.dp),
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
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Versió 1.0.0",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        
        // Contenido principal (60%)
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Informació",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = "Sobre l'aplicació",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Trivia Legends és un joc de preguntes i respostes desenvolupat per estudiants de La Salle. Aquest projecte forma part de l'assignatura de Desenvolupament d'Aplicacions Mòbils i té com a objectiu demostrar els coneixements adquirits en el desenvolupament d'aplicacions Android utilizando Kotlin i Jetpack Compose.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        
        // Panel lateral derecho (20%)
        Card(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
                .padding(start = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Desenvolupadors",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                DeveloperCard(
                    name = "Pol Hernández",
                    role = "Desenvolupador"
                )
                
                DeveloperCard(
                    name = "Teo Aranda",
                    role = "Desenvolupador"
                )
            }
        }
    }
}

@Composable
private fun DeveloperCard(name: String, role: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 