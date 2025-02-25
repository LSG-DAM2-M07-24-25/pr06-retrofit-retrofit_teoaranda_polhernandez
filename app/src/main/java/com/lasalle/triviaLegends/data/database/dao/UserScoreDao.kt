package com.lasalle.triviaLegends.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lasalle.triviaLegends.data.database.entities.UserScore
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) per gestionar les operacions amb les puntuacions
 * Utilitzem Flow per tenir actualitzacions reactives de les dades
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He utilitzat Flow en lloc de LiveData perquè és més modern i flexible
 * 
 * NOTA POL: Sí, i a més ens permet fer transformacions de dades més fàcilment
 * Potser podríem afegir una query per obtenir estadístiques per dificultat?
 */
@Dao
interface UserScoreDao {
    /**
     * Insereix una nova puntuació a la base de dades
     * 
     * @param score Puntuació a guardar
     */
    @Insert
    suspend fun insertScore(score: UserScore)
    
    /**
     * Obté totes les puntuacions ordenades per data descendent
     * Utilitzem Flow per rebre actualitzacions automàtiques
     */
    @Query("SELECT * FROM user_scores ORDER BY date DESC")
    fun getAllScores(): Flow<List<UserScore>>
    
    /**
     * Obté la millor puntuació (la més alta)
     * Retorna null si no hi ha cap puntuació
     */
    @Query("SELECT * FROM user_scores ORDER BY score DESC LIMIT 1")
    fun getBestScore(): Flow<UserScore?>
    
    /**
     * Calcula la puntuació mitjana de totes les partides
     * 
     * NOTA TEO: Podríem filtrar per dificultat també
     * NOTA POL: Ho afegirem a la fase 2 quan implementem els filtres
     */
    @Query("SELECT AVG(score) FROM user_scores")
    fun getAverageScore(): Flow<Float>
} 