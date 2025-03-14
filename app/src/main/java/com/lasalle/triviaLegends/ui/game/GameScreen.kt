package com.lasalle.triviaLegends.ui.game

import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.Composable
import com.lasalle.triviaLegends.ui.game.medium.GameScreenMedium
import com.lasalle.triviaLegends.ui.game.small.GameScreenSmall
import com.lasalle.triviaLegends.ui.game.large.GameScreenLarge

/**
 * Pantalla principal del joc
 * Selecciona la pantalla adequada segons la mida del dispositiu
 * 
 * @author Pol & Teo
 */
@Composable
fun GameScreen() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    
    when {
        screenWidth < 600.dp -> {
            GameScreenSmall()
        }
        screenWidth < 840.dp -> {
            GameScreenMedium()
        }
        else -> {
            GameScreenLarge()
        }
    }
} 