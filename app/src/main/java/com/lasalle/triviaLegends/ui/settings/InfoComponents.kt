package com.lasalle.triviaLegends.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Secció d'informació
 * Component reutilitzable per mostrar una secció d'informació amb títol i contingut
 * 
 * @param title Títol de la secció
 * @param content Contingut de la secció (composable)
 */
@Composable
fun InfoSection(
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

/**
 * Secció sobre l'API
 * Component que mostra informació sobre l'API utilitzada
 */
@Composable
fun ApiInfoSection() {
    InfoSection(title = "Sobre l'API") {
        Column(
            modifier = Modifier.fillMaxWidth()
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
}

/**
 * Secció de Sistema de Puntuació
 * Component que mostra informació sobre el sistema de puntuació
 */
@Composable
fun ScoringSystemSection() {
    InfoSection(title = "Sistema de Puntuació") {
        Column(
            modifier = Modifier.fillMaxWidth()
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
}

/**
 * Secció d'Agraïments
 * Component que mostra informació sobre els desenvolupadors i agraïments
 */
@Composable
fun CreditsSection() {
    InfoSection(title = "Agraïments") {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Trivia Legends",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Versió 1.0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            Text(
                text = "Desenvolupat per:",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Pol Hernández i Teo Aranda",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            Text(
                text = "Agraïments especials a OpenTDB per proporcionar l'API que fa possible aquesta aplicació.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * Targeta de desenvolupador
 * Component que mostra informació sobre un desenvolupador
 * 
 * @param name Nom del desenvolupador
 * @param role Rol del desenvolupador (opcional)
 */
@Composable
fun DeveloperCard(
    name: String,
    role: String = "Desenvolupador"
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 