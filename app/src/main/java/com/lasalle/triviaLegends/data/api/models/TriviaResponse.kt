package com.lasalle.triviaLegends.data.api.models

import com.google.gson.annotations.SerializedName

/**
 * Model per la resposta de l'API de OpenTrivia
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He afegit documentació dels codis de resposta per tenir-ho a mà
 * 
 * Codis de resposta de l'API:
 * - 0: Success (Tot correcte)
 * - 1: No Results (No hi ha prou preguntes per la query)
 * - 2: Invalid Parameter (Paràmetres invàlids)
 * - 3: Token Not Found (Token de sessió no trobat)
 * - 4: Token Empty (Token sense preguntes disponibles)
 */
data class TriviaResponse(
    // Codi de resposta de l'API
    @SerializedName("response_code")
    val responseCode: Int,
    
    // Llista de preguntes rebudes
    @SerializedName("results")
    val results: List<Question>
) {
    /**
     * Comprova si la resposta és correcta
     * 
     * NOTA TEO: Això ens anirà bé per gestionar errors a la UI
     */
    fun isSuccessful(): Boolean = responseCode == 0
} 