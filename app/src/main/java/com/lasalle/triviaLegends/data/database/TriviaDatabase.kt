package com.lasalle.triviaLegends.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lasalle.triviaLegends.data.database.dao.UserScoreDao
import com.lasalle.triviaLegends.data.database.entities.UserScore

// Base de dades principal de l'aplicació
@Database(
    entities = [UserScore::class],
    version = 1,
    exportSchema = false
)
abstract class TriviaDatabase : RoomDatabase() {
    // DAO per accedir a les puntuacions
    abstract fun userScoreDao(): UserScoreDao
} 