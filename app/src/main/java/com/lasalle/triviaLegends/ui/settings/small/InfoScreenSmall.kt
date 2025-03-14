package com.lasalle.triviaLegends.ui.settings.small

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight

/**
 * Pantalla d'informació per a dispositius petits (mòbils)
 * Mostra un disseny vertical amb tota la informació en una columna
 * 
 * @author Pol & Teo
 */
@Composable
fun InfoScreenSmall(
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
        ApiInfoSection()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Secció de Sistema de Puntuació
        ScoringSystemSection()
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Secció d'Agraïments
        CreditsSection()
    }
} 