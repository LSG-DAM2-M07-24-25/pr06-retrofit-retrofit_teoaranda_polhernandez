package com.lasalle.triviaLegends.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entitat per emmagatzemar les puntuacions dels usuaris
 * Utilitzem Room per persistir les dades localment
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He afegit un timestamp automàtic amb System.currentTimeMillis()
 * així podem ordenar per data sense haver de passar-la explícitament
 * 
 * NOTA POL: Bona idea! També podríem afegir el nom del jugador en una futura versió
 * per tenir un ranking amb diferents usuaris
 */
@Entity(tableName = "user_scores")
data class UserScore(
    // ID únic autogenerat per cada puntuació
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Timestamp de quan s'ha guardat la puntuació
    val date: Long = System.currentTimeMillis(),
    
    // Puntuació total obtinguda en la partida
    val score: Int,
    
    // Dificultat seleccionada (easy, medium, hard)
    val difficulty: String,
    
    // Número de respostes correctes
    val correctAnswers: Int,
    
    // Número total de preguntes de la partida
    val totalQuestions: Int
) {
    /**
     * Calcula el percentatge d'encert
     * 
     * NOTA POL: Això ens anirà bé per mostrar estadístiques
     */
    fun getSuccessRate(): Float = (correctAnswers.toFloat() / totalQuestions) * 100
} 