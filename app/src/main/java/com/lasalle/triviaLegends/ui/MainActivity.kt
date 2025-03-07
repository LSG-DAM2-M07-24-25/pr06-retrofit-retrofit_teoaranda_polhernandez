package com.lasalle.triviaLegends.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lasalle.triviaLegends.ui.game.GameScreen
import com.lasalle.triviaLegends.ui.scores.ScoresScreen
import com.lasalle.triviaLegends.ui.settings.InfoScreen
import com.lasalle.triviaLegends.ui.theme.TriviaLegendsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activitat principal de l'aplicació
 * Configura la navegació i el tema
 * 
 * @author Pol & Teo
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaLegendsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TriviaApp()
                }
            }
        }
    }
}

/**
 * Composable principal de l'aplicació
 * Configura la navegació entre pantalles
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Game.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Game.route) {
                GameScreen()
            }
            composable(Screen.Scores.route) {
                ScoresScreen()
            }
            composable(Screen.Info.route) {
                InfoScreen()
            }
        }
    }
}

/**
 * Barra de navegació inferior
 */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        Screen.items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = navController.currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

/**
 * Classe segellada que representa les diferents pantalles de l'aplicació
 */
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Game : Screen(
        "game",
        "Jugar",
        Icons.Default.PlayArrow
    )
    
    object Scores : Screen(
        "scores",
        "Puntuacions",
        Icons.Default.List
    )
    
    object Info : Screen(
        "info",
        "Info",
        Icons.Default.Info
    )
    
    companion object {
        val items = listOf(Game, Scores, Info)
    }
} 