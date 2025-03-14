package com.lasalle.triviaLegends.ui.settings

import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.Composable
import com.lasalle.triviaLegends.ui.settings.small.InfoScreenSmall
import com.lasalle.triviaLegends.ui.settings.medium.InfoScreenMedium
import com.lasalle.triviaLegends.ui.settings.large.InfoScreenLarge

/**
 * Pantalla principal d'informació
 * Selecciona la pantalla adequada segons la mida del dispositiu
 * 
 * @author Pol & Teo
 */
@Composable
fun InfoScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    when {
        screenWidth < 600.dp -> {
            InfoScreenSmall()
        }
        screenWidth < 840.dp -> {
            InfoScreenMedium()
        }
        else -> {
            InfoScreenLarge()
        }
    }
} 