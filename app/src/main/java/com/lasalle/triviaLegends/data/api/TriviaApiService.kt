package com.lasalle.triviaLegends.data.api

import com.lasalle.triviaLegends.data.api.models.TriviaResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfície per les crides a l'API de OpenTrivia
 * Utilitzem Retrofit per gestionar les crides HTTP
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He posat els paràmetres opcionals amb valor per defecte
 * així podem fer crides més simples quan vulguem.
 * 
 * NOTA POL: Perfecte! També he afegit les constants per la URL i
 * les dificultats, així evitem errors d'escriptura.
 * 
 * Exemple d'ús:
 * ```
 * // Obtenir 10 preguntes de qualsevol categoria en mode fàcil
 * api.getQuestions(amount = 10, difficulty = DIFFICULTY_EASY)
 * ```
 */
interface TriviaApiService {
    
    /**
     * Obté preguntes de l'API segons els paràmetres especificats
     * 
     * @param amount Número de preguntes a obtenir
     * @param category ID de la categoria (opcional)
     * @param difficulty Nivell de dificultat (opcional)
     * @param type Tipus de pregunta (per defecte "multiple")
     * 
     * NOTA POL: Hauríem d'afegir una funció per obtenir les categories?
     * NOTA TEO: Sí, ho podem fer a la fase 2 quan implementem el selector de categories
     */
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
        
        // Constants per les dificultats disponibles
        const val DIFFICULTY_EASY = "easy"
        const val DIFFICULTY_MEDIUM = "medium"
        const val DIFFICULTY_HARD = "hard"
        
        // Valor per defecte de preguntes a demanar
        const val DEFAULT_QUESTIONS_AMOUNT = 10
    }
} 