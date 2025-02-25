package com.lasalle.triviaLegends.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entitat per emmagatzemar les puntuacions dels usuaris
@Entity(tableName = "user_scores")
data class UserScore(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Data de la partida
    val date: Long = System.currentTimeMillis(),
    
    // Puntuació obtinguda
    val score: Int,
    
    // Dificultat seleccionada
    val difficulty: String,
    
    // Número de preguntes contestades correctament
    val correctAnswers: Int,
    
    // Número total de preguntes
    val totalQuestions: Int
) 