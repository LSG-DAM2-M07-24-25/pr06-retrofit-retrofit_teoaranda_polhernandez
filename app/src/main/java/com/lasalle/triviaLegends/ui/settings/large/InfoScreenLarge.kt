package com.lasalle.triviaLegends.ui.settings.large

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
 * Pantalla d'informació per a dispositius grans (tablets, ordinadors)
 * Mostra un disseny amb dos panells laterals i el contingut principal al centre
 * 
 * @author Pol & Teo
 */
@Composable
fun InfoScreenLarge(
    viewModel: InfoViewModel = hiltViewModel()
) {
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
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Versió 1.0",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "API",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "OpenTDB",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        
        // Contingut principal (60%)
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Informació",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            // Secció sobre l'API
            ApiInfoSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Secció de Sistema de Puntuació
            ScoringSystemSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Secció d'Agraïments
            CreditsSection()
        }
        
        // Panell lateral dret (20%)
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
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.Bold
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