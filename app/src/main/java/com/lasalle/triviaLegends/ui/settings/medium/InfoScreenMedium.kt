package com.lasalle.triviaLegends.ui.settings.medium

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.settings.InfoViewModel
import com.lasalle.triviaLegends.ui.settings.ApiInfoSection
import com.lasalle.triviaLegends.ui.settings.ScoringSystemSection
import com.lasalle.triviaLegends.ui.settings.CreditsSection
import com.lasalle.triviaLegends.ui.settings.DeveloperCard
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight

/**
 * Pantalla d'informació per a dispositius mitjans (tablets petites, mòbils en horitzontal)
 * Mostra un disseny amb un panell lateral esquerre i el contingut principal a la dreta
 * 
 * @author Pol & Teo
 */
@Composable
fun InfoScreenMedium(
    viewModel: InfoViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Panell lateral (30%)
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
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Versió 1.0",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Desenvolupadors",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
                
                DeveloperCard("Pol Hernández")
                DeveloperCard("Teo Aranda")
            }
        }
        
        // Contingut principal (70%)
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Informació",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Secció sobre l'API
            ApiInfoSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Secció de Sistema de Puntuació
            ScoringSystemSection()
        }
    }
} 