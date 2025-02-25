package com.lasalle.triviaLegends.data.api.models

import com.google.gson.annotations.SerializedName

// Model per la resposta de l'API
// Conté el codi de resposta i la llista de preguntes
data class TriviaResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    
    @SerializedName("results")
    val results: List<Question>
) 