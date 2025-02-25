package com.lasalle.triviaLegends.data.repository

import com.lasalle.triviaLegends.data.api.TriviaApiService
import com.lasalle.triviaLegends.data.api.models.Question
import com.lasalle.triviaLegends.data.database.dao.UserScoreDao
import com.lasalle.triviaLegends.data.database.entities.UserScore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

// Repositori principal que gestiona l'accés a dades
@Singleton
class TriviaRepository @Inject constructor(
    private val apiService: TriviaApiService,
    private val userScoreDao: UserScoreDao
) {
    // Obtenir preguntes de l'API
    suspend fun getQuestions(
        amount: Int,
        category: Int? = null,
        difficulty: String? = null
    ): List<Question> {
        return apiService.getQuestions(amount, category, difficulty).results
    }
    
    // Guardar una nova puntuació
    suspend fun saveScore(score: UserScore) {
        userScoreDao.insertScore(score)
    }
    
    // Obtenir totes les puntuacions
    fun getAllScores(): Flow<List<UserScore>> {
        return userScoreDao.getAllScores()
    }
    
    // Obtenir la millor puntuació
    fun getBestScore(): Flow<UserScore?> {
        return userScoreDao.getBestScore()
    }
    
    // Obtenir la puntuació mitjana
    fun getAverageScore(): Flow<Float> {
        return userScoreDao.getAverageScore()
    }
} 