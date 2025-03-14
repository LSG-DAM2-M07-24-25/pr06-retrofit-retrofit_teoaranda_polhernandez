package com.lasalle.triviaLegends.ui.game.small

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
 * Pantalla de joc per a dispositius petits (mòbils)
 * Mostra un disseny vertical amb tota la informació en una columna
 * 
 * @author Pol & Teo
 */
@Composable
fun GameScreenSmall(
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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