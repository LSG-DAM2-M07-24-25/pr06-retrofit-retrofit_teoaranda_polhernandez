package com.lasalle.triviaLegends.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasalle.triviaLegends.data.api.TriviaApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel per la pantalla de configuració
 * Gestiona les preferències de l'usuari
 * 
 * @author Pol & Teo
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    // Dificultat predeterminada
    private val _defaultDifficulty = MutableStateFlow(TriviaApiService.DIFFICULTY_MEDIUM)
    val defaultDifficulty: StateFlow<String> = _defaultDifficulty.asStateFlow()
    
    // Temps per pregunta (en segons)
    private val _timePerQuestion = MutableStateFlow(30)
    val timePerQuestion: StateFlow<Int> = _timePerQuestion.asStateFlow()
    
    /**
     * Canvia la dificultat predeterminada
     * 
     * @param difficulty Nova dificultat
     */
    fun setDefaultDifficulty(difficulty: String) {
        viewModelScope.launch {
            _defaultDifficulty.value = when (difficulty) {
                TriviaApiService.DIFFICULTY_EASY, 
                TriviaApiService.DIFFICULTY_MEDIUM, 
                TriviaApiService.DIFFICULTY_HARD -> difficulty
                else -> TriviaApiService.DIFFICULTY_MEDIUM
            }
            // Aquí podríem guardar la preferència en DataStore
        }
    }
    
    /**
     * Canvia el temps per pregunta
     * 
     * @param seconds Segons per pregunta (entre 10 i 60)
     */
    fun setTimePerQuestion(seconds: Int) {
        viewModelScope.launch {
            _timePerQuestion.value = seconds.coerceIn(10, 60)
            // Aquí podríem guardar la preferència en DataStore
        }
    }
    
    /**
     * Llista dedifficulties disponibles
     */
    fun getAvailableDifficulties(): List<Pair<String, String>> {
        return listOf(
            TriviaApiService.DIFFICULTY_EASY to "Fàcil",
            TriviaApiService.DIFFICULTY_MEDIUM to "Mitjana",
            TriviaApiService.DIFFICULTY_HARD to "Difícil"
        )
    }
    
    /**
     * Llista de temps disponibles
     */
    fun getAvailableTimes(): List<Int> {
        return listOf(10, 15, 20, 30, 45, 60)
    }
} 