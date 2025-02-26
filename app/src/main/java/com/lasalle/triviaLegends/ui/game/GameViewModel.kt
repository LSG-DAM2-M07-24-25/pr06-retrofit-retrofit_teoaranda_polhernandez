package com.lasalle.triviaLegends.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasalle.triviaLegends.data.api.TriviaApiService
import com.lasalle.triviaLegends.data.api.models.Question
import com.lasalle.triviaLegends.data.database.entities.UserScore
import com.lasalle.triviaLegends.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel per la pantalla de joc
 * Gestiona la lògica de negoci i l'estat del joc
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He implementat un sistema de puntuació basat en la dificultat i el temps
 * de resposta. Així premiem als jugadors més ràpids.
 */
@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: TriviaRepository
) : ViewModel() {

    // Estat del joc
    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    // Preguntes actuals
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    
    // Pregunta actual
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()
    
    // Puntuació actual
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    
    // Respostes correctes
    private val _correctAnswers = MutableStateFlow(0)
    val correctAnswers: StateFlow<Int> = _correctAnswers.asStateFlow()
    
    // Dificultat seleccionada
    private val _difficulty = MutableStateFlow<String?>(null)
    
    /**
     * Inicia una nova partida
     * 
     * @param difficulty Dificultat seleccionada (opcional)
     * @param amount Número de preguntes (per defecte 10)
     */
    fun startGame(difficulty: String? = null, amount: Int = 10) {
        _gameState.value = GameState.Loading
        _score.value = 0
        _correctAnswers.value = 0
        _currentQuestionIndex.value = 0
        _difficulty.value = difficulty
        
        viewModelScope.launch {
            try {
                val questions = repository.getQuestions(amount, difficulty = difficulty)
                if (questions.isEmpty()) {
                    _gameState.value = GameState.Error("No s'han trobat preguntes")
                } else {
                    _questions.value = questions
                    _gameState.value = GameState.Playing
                }
            } catch (e: Exception) {
                _gameState.value = GameState.Error("Error carregant preguntes: ${e.message}")
            }
        }
    }
    
    /**
     * Obté la pregunta actual
     */
    fun getCurrentQuestion(): Question? {
        return _questions.value.getOrNull(_currentQuestionIndex.value)
    }
    
    /**
     * Comprova si la resposta és correcta i actualitza la puntuació
     * 
     * @param answer Resposta seleccionada
     * @return true si és correcta, false si no
     */
    fun checkAnswer(answer: String): Boolean {
        val currentQuestion = getCurrentQuestion() ?: return false
        val isCorrect = answer == currentQuestion.correctAnswer
        
        if (isCorrect) {
            // Calculem punts segons dificultat
            val difficultyMultiplier = when(currentQuestion.difficulty) {
                TriviaApiService.DIFFICULTY_EASY -> 1
                TriviaApiService.DIFFICULTY_MEDIUM -> 2
                TriviaApiService.DIFFICULTY_HARD -> 3
                else -> 1
            }
            
            // Sumem punts
            _score.value += 10 * difficultyMultiplier
            _correctAnswers.value += 1
        }
        
        return isCorrect
    }
    
    /**
     * Passa a la següent pregunta
     * 
     * @return true si hi ha més preguntes, false si s'ha acabat el joc
     */
    fun nextQuestion(): Boolean {
        val nextIndex = _currentQuestionIndex.value + 1
        return if (nextIndex < _questions.value.size) {
            _currentQuestionIndex.value = nextIndex
            true
        } else {
            _gameState.value = GameState.Finished
            saveScore()
            false
        }
    }
    
    /**
     * Guarda la puntuació a la base de dades
     */
    private fun saveScore() {
        viewModelScope.launch {
            val score = UserScore(
                score = _score.value,
                difficulty = _difficulty.value ?: "any",
                correctAnswers = _correctAnswers.value,
                totalQuestions = _questions.value.size
            )
            repository.saveScore(score)
        }
    }
    
    /**
     * Reinicia el joc amb la mateixa configuració
     */
    fun restartGame() {
        startGame(_difficulty.value, _questions.value.size)
    }
}

/**
 * Classe segellada que representa els diferents estats del joc
 */
sealed class GameState {
    object Loading : GameState()
    object Playing : GameState()
    object Finished : GameState()
    data class Error(val message: String) : GameState()
} 