package com.lasalle.triviaLegends.ui.scores

import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.Composable
import com.lasalle.triviaLegends.ui.scores.small.ScoresScreenSmall
import com.lasalle.triviaLegends.ui.scores.medium.ScoresScreenMedium
import com.lasalle.triviaLegends.ui.scores.large.ScoresScreenLarge

/**
 * Pantalla principal de puntuacions
 * Selecciona la pantalla adequada segons la mida del dispositiu
 * 
 * @author Pol & Teo
 */
@Composable
fun ScoresScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    when {
        screenWidth < 600.dp -> {
            ScoresScreenSmall()
        }
        screenWidth < 840.dp -> {
            ScoresScreenMedium()
        }
        else -> {
            ScoresScreenLarge()
        }
    }
} 