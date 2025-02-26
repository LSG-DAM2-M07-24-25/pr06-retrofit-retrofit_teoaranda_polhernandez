package com.lasalle.triviaLegends.ui.scores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasalle.triviaLegends.data.database.entities.UserScore
import com.lasalle.triviaLegends.data.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    
    // Totes les puntuacions
    val scores: StateFlow<List<ScoreUiModel>> = repository.getAllScores()
        .map { scores ->
            scores.map { it.toUiModel() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    // Millor puntuació
    val bestScore: StateFlow<ScoreUiModel?> = repository.getBestScore()
        .map { score -> score?.toUiModel() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )
    
    // Puntuació mitjana
    val averageScore: StateFlow<Float> = repository.getAverageScore()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0f
        )
    
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