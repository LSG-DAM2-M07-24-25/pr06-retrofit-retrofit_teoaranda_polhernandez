package com.lasalle.triviaLegends.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lasalle.triviaLegends.data.database.dao.UserScoreDao
import com.lasalle.triviaLegends.data.database.entities.UserScore

/**
 * Base de dades principal de l'aplicació
 * Utilitzem Room per gestionar la persistència de dades
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: De moment només tenim una entitat, però podríem afegir més
 * com per exemple preguntes guardades per mode offline.
 */
@Database(
    entities = [UserScore::class],
    version = 1,
    exportSchema = false
)
abstract class TriviaDatabase : RoomDatabase() {
    /**
     * DAO per accedir a les puntuacions
     */
    abstract fun userScoreDao(): UserScoreDao
} 