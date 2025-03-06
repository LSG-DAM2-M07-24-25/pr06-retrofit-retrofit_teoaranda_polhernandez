package com.lasalle.triviaLegends.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Pantalla de configuració
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He simplificat aquesta pantalla segons els requisits,
 * només mostrem la dificultat predeterminada i el temps per pregunta.
 */
@Composable
fun SettingsScreen(
    viewModel: InfoViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Informació",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Secció sobre l'API
        SettingsSection(title = "Sobre l'API") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Aquesta aplicació utilitza l'API de Open Trivia Database (OpenTDB)",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "OpenTDB és una base de dades de preguntes trivia completament gratuïta que proporciona una API JSON per a projectes de programació.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Totes les dades proporcionades per l'API estan disponibles sota la llicència Creative Commons Attribution-ShareAlike 4.0 International.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Visita opentdb.com per a més informació.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Secció de Sistema de Puntuació
        SettingsSection(title = "Sistema de Puntuació") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Com es calcula la puntuació:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "• Resposta correcta: +10 punts",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "• Resposta incorrecta: 0 punts",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "• Temps extra: fins a +5 punts addicionals",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "• Dificultat:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "  - Fàcil: x1.0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "  - Mitjana: x1.5",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "  - Difícil: x2.0",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Secció d'Agraïments
        SettingsSection(title = "Agraïments") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Trivia Legends",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Versió 1.0",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Desenvolupat per:",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Text(
                    text = "Pol Hernández i Teo Aranda",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Agraïments especials a OpenTDB per proporcionar l'API que fa possible aquesta aplicació.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

/**
 * Secció de configuració
 */
@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            content()
        }
    }
} 