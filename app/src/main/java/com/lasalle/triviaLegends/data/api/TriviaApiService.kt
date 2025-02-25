package com.lasalle.triviaLegends.data.api

import com.lasalle.triviaLegends.data.api.models.TriviaResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interfície que defineix els endpoints de l'API
interface TriviaApiService {
    
    // Endpoint principal per obtenir preguntes
    // Permet especificar la quantitat, categoria i dificultat
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String = "multiple"
    ): TriviaResponse

    companion object {
        // URL base de l'API
        const val BASE_URL = "https://opentdb.com/"
        
        // Constants per a les dificultats
        const val DIFFICULTY_EASY = "easy"
        const val DIFFICULTY_MEDIUM = "medium"
        const val DIFFICULTY_HARD = "hard"
    }
} 