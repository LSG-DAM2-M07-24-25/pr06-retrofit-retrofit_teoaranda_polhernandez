package com.lasalle.triviaLegends.data.repository

import com.lasalle.triviaLegends.data.api.TriviaApiService
import com.lasalle.triviaLegends.data.api.models.Question
import com.lasalle.triviaLegends.data.database.dao.UserScoreDao
import com.lasalle.triviaLegends.data.database.entities.UserScore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositori principal que gestiona l'accés a dades
 * Actua com a Single Source of Truth de l'aplicació
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He afegit @Singleton per assegurar que només hi ha una instància
 * 
 * NOTA TEO: Bona idea! També he posat @Inject constructor per Dagger Hilt
 * així no hem de crear el repositori manualment
 * 
 * Exemple d'ús des del ViewModel:
 * ```
 * class GameViewModel @Inject constructor(
 *     private val repository: TriviaRepository
 * )
 * ```
 */
@Singleton
class TriviaRepository @Inject constructor(
    private val apiService: TriviaApiService,
    private val userScoreDao: UserScoreDao
) {
    /**
     * Obté preguntes de l'API
     * 
     * @param amount Número de preguntes a obtenir
     * @param category Categoria opcional
     * @param difficulty Dificultat opcional
     * 
     * NOTA TEO: Hauríem d'afegir cache de preguntes?
     * NOTA POL: Bona idea, ho podem fer amb Room a la fase 2
     */
    suspend fun getQuestions(
        amount: Int = TriviaApiService.DEFAULT_QUESTIONS_AMOUNT,
        category: Int? = null,
        difficulty: String? = null
    ): List<Question> {
        return apiService.getQuestions(amount, category, difficulty).results
    }
    
    /**
     * Guarda una nova puntuació
     * 
     * @param score Puntuació a guardar
     */
    suspend fun saveScore(score: UserScore) {
        userScoreDao.insertScore(score)
    }
    
    /**
     * Obté totes les puntuacions ordenades per data
     * @return Flow amb la llista de puntuacions
     */
    fun getAllScores(): Flow<List<UserScore>> {
        return userScoreDao.getAllScores()
    }
    
    /**
     * Obté la millor puntuació
     * @return Flow amb la millor puntuació o null si no n'hi ha cap

    fun getBestScore(): Flow<UserScore?> {
        return userScoreDao.getBestScore()
    }

     * Obté la puntuació mitjana
     * @return Flow amb la mitjana de puntuacions

    fun getAverageScore(): Flow<Float> {
        return userScoreDao.getAverageScore()
    }
    **/
} 