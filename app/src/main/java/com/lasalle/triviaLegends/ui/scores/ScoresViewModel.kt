package com.lasalle.triviaLegends.ui.scores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasalle.triviaLegends.data.database.entities.UserScore
import com.lasalle.triviaLegends.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel per la pantalla de puntuacions
 * Gestiona la visualització de les puntuacions i estadístiques
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He afegit transformacions per mostrar les dates en format llegible
 * i calcular estadístiques addicionals.
 */
@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val repository: TriviaRepository
) : ViewModel() {

    // Format de data
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    private val _scores = MutableStateFlow<List<ScoreUiModel>>(emptyList())
    val scores: StateFlow<List<ScoreUiModel>> = _scores.asStateFlow()
    
    private val _bestScore = MutableStateFlow<ScoreUiModel?>(null)
    val bestScore: StateFlow<ScoreUiModel?> = _bestScore.asStateFlow()
    
    private val _averageScore = MutableStateFlow(0f)
    val averageScore: StateFlow<Float> = _averageScore.asStateFlow()
    
    private val _worstScore = MutableStateFlow<ScoreUiModel?>(null)
    val worstScore: StateFlow<ScoreUiModel?> = _worstScore.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getAllScores().collect { scores ->
                val scoreModels = scores.map { it.toUiModel() }
                _scores.value = scoreModels
                
                // Update best score
                _bestScore.value = scoreModels.maxByOrNull { it.score }
                
                // Update worst score
                _worstScore.value = scoreModels.minByOrNull { it.score }
                
                // Update average score
                _averageScore.value = if (scoreModels.isNotEmpty()) {
                    scoreModels.map { it.score }.average().toFloat()
                } else {
                    0f
                }
            }
        }
    }
    
    /**
     * Converteix un UserScore a un model per la UI
     */
    private fun UserScore.toUiModel(): ScoreUiModel {
        return ScoreUiModel(
            id = id,
            score = score,
            formattedDate = dateFormat.format(Date(date)),
            difficulty = when (difficulty) {
                "easy" -> "Fàcil"
                "medium" -> "Mitjana"
                "hard" -> "Difícil"
                else -> difficulty
            },
            successRate = getSuccessRate(),
            correctAnswers = correctAnswers,
            totalQuestions = totalQuestions
        )
    }
}

/**
 * Model de dades per la UI de puntuacions
 */
data class ScoreUiModel(
    val id: Long,
    val score: Int,
    val formattedDate: String,
    val difficulty: String,
    val successRate: Float,
    val correctAnswers: Int,
    val totalQuestions: Int
) 