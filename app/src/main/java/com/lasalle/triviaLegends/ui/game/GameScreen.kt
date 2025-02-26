package com.lasalle.triviaLegends.ui.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lasalle.triviaLegends.data.api.models.Question
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.CircleShape
import android.text.Html
import android.os.Build

fun String.decodeHtml(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this).toString()
    }
}

/**
 * Pantalla principal del joc
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He afegit animacions per fer l'experiència més fluida
 */
@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val correctAnswers by viewModel.correctAnswers.collectAsState()
    
    // Iniciar el joc si encara no s'ha iniciat
    LaunchedEffect(key1 = Unit) {
        if (gameState == GameState.Loading) {
            viewModel.startGame()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        when (gameState) {
            GameState.Loading -> LoadingScreen()
            GameState.Playing -> {
                val currentQuestion = viewModel.getCurrentQuestion()
                if (currentQuestion != null) {
                    QuestionScreen(
                        question = currentQuestion,
                        questionIndex = currentQuestionIndex,
                        totalQuestions = 10, // Hauria de venir del ViewModel
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
                totalQuestions = 10, // Hauria de venir del ViewModel
                onPlayAgain = { viewModel.restartGame() }
            )
            is GameState.Error -> ErrorScreen(
                message = (gameState as GameState.Error).message,
                onRetry = { viewModel.startGame() }
            )
        }
    }
}

/**
 * Pantalla de càrrega
 */
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Carregant preguntes...")
        }
    }
}

/**
 * Pantalla d'error
 */
@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = "Tornar a intentar")
            }
        }
    }
}

/**
 * Pantalla de pregunta
 */
@Composable
fun QuestionScreen(
    question: Question,
    questionIndex: Int,
    totalQuestions: Int,
    score: Int,
    onAnswerSelected: (String) -> Unit
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var showFeedback by remember { mutableStateOf(false) }
    
    LaunchedEffect(isAnswered) {
        if (isAnswered) {
            showFeedback = true
            delay(800) // Reducimos un poco el tiempo para el feedback
            showFeedback = false
            delay(200) // Esperamos a que se complete la animación de fade out
            onAnswerSelected(selectedAnswer ?: return@LaunchedEffect)
            selectedAnswer = null
            isAnswered = false
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                // Capçalera amb progrés i puntuació
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pregunta ${questionIndex + 1}/$totalQuestions",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Puntuació: $score",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Barra de progrés
                LinearProgressIndicator(
                    progress = (questionIndex.toFloat() + 1) / totalQuestions,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Categoria i dificultat
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = question.category,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = when(question.difficulty) {
                            "easy" -> "Fàcil"
                            "medium" -> "Mitjana"
                            "hard" -> "Difícil"
                            else -> question.difficulty
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = when(question.difficulty) {
                            "easy" -> Color.Green
                            "medium" -> Color.Blue
                            "hard" -> Color.Red
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Pregunta
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = question.question.decodeHtml(),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // Respostes
            items(question.getAllAnswers()) { answer ->
                AnswerCard(
                    answer = answer,
                    isSelected = selectedAnswer == answer,
                    isCorrect = if (isAnswered) answer == question.correctAnswer else null,
                    isEnabled = !isAnswered,
                    onClick = {
                        if (!isAnswered) {
                            selectedAnswer = answer
                            isAnswered = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        
        // Feedback overlay
        AnimatedVisibility(
            visible = showFeedback,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200)),
            modifier = Modifier.align(Alignment.Center)
        ) {
            val isCorrect = selectedAnswer == question.correctAnswer
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Aura background
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(200)) + 
                            scaleIn(
                                initialScale = 1.5f,
                                animationSpec = tween(500)
                            )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = if (isCorrect) {
                                    Color.Green.copy(alpha = 0.15f)
                                } else {
                                    Color.Red.copy(alpha = 0.15f)
                                }
                            )
                    )
                }
                
                // Text
                Text(
                    text = if (isCorrect) "Correcte!" else "Incorrecte!",
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isCorrect) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Targeta de resposta
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerCard(
    answer: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect == true -> Color.Green.copy(alpha = 0.2f)
        isCorrect == false && isSelected -> Color.Red.copy(alpha = 0.2f)
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    
    ElevatedCard(
        onClick = { if (isEnabled) onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = backgroundColor
        ),
        enabled = isEnabled
    ) {
        Text(
            text = answer.decodeHtml(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Pantalla de joc finalitzat
 */
@Composable
fun GameFinishedScreen(
    score: Int,
    correctAnswers: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = Unit) {
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Joc Finalitzat!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Puntuació Final",
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Respostes correctes: $correctAnswers/$totalQuestions",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Text(
                        text = "Percentatge d'encert: ${(correctAnswers.toFloat() / totalQuestions * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = "Tornar a jugar")
            }
        }
    }
} 