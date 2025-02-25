package com.lasalle.triviaLegends.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lasalle.triviaLegends.data.database.entities.UserScore
import kotlinx.coroutines.flow.Flow

// DAO per gestionar les operacions amb les puntuacions
@Dao
interface UserScoreDao {
    // Inserir una nova puntuació
    @Insert
    suspend fun insertScore(score: UserScore)
    
    // Obtenir totes les puntuacions ordenades per data
    @Query("SELECT * FROM user_scores ORDER BY date DESC")
    fun getAllScores(): Flow<List<UserScore>>
    
    // Obtenir la millor puntuació
    @Query("SELECT * FROM user_scores ORDER BY score DESC LIMIT 1")
    fun getBestScore(): Flow<UserScore?>
    
    // Obtenir la puntuació mitjana
    @Query("SELECT AVG(score) FROM user_scores")
    fun getAverageScore(): Flow<Float>
} 