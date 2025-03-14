package com.lasalle.triviaLegends.ui.game.medium

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
 * Pantalla de joc per a dispositius mitjans (tablets petites, mòbils en horitzontal)
 * Mostra un disseny amb un panell lateral esquerre més petit i el contingut principal a la dreta
 * 
 * @author Pol & Teo
 */
@Composable
fun GameScreenMedium(
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
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Panell lateral amb informació del joc (30% de l'amplada)
        Card(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .padding(end = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Puntuació: $score",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Pregunta: ${currentQuestionIndex + 1}/10",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        
        // Contingut principal (70% de l'amplada)
        Box(
            modifier = Modifier
                .weight(0.7f)
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