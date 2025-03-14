package com.lasalle.triviaLegends.ui.game.large

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.ui.game.GameViewModel
import com.lasalle.triviaLegends.ui.game.GameState
import com.lasalle.triviaLegends.ui.game.LoadingScreen
import com.lasalle.triviaLegends.ui.game.ErrorScreen
import com.lasalle.triviaLegends.ui.game.QuestionScreen
import com.lasalle.triviaLegends.ui.game.GameFinishedScreen

/**
 * Pantalla de joc per a dispositius grans (tablets, ordinadors)
 * Mostra un disseny amb un panell lateral esquerre i el contingut principal a la dreta
 * 
 * @author Pol & Teo
 */
@Composable
fun GameScreenLarge(
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val correctAnswers by viewModel.correctAnswers.collectAsState()
    
    LaunchedEffect(key1 = Unit) {
        if (gameState == GameState.Loading) {
            viewModel.startGame()
        }
    }
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Panell lateral esquerre (20%)
        Card(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
                .padding(end = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Puntuació",
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Divider()
                
                Text(
                    text = "Pregunta",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Text(
                    text = "${currentQuestionIndex + 1}/10",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                LinearProgressIndicator(
                    progress = (currentQuestionIndex.toFloat() + 1) / 10,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }
        
        // Contingut principal (80%)
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight()
        ) {
            when (gameState) {
                GameState.Loading -> LoadingScreen()
                GameState.Playing -> {
                    val currentQuestion = viewModel.getCurrentQuestion()
                    if (currentQuestion != null) {
                        QuestionScreen(
                            question = currentQuestion,
                            questionIndex = currentQuestionIndex,
                            totalQuestions = 10,
                            score = score,
                            onAnswerSelected = { answer ->
                                val isCorrect = viewModel.checkAnswer(answer)
                                viewModel.nextQuestion()
                            }
                        )
                    }
                }
                GameState.Finished -> GameFinishedScreen(
                    score = score,
                    correctAnswers = correctAnswers,
                    totalQuestions = 10,
                    onPlayAgain = { viewModel.restartGame() }
                )
                is GameState.Error -> ErrorScreen(
                    message = (gameState as GameState.Error).message,
                    onRetry = { viewModel.startGame() }
                )
            }
        }
    }
} 