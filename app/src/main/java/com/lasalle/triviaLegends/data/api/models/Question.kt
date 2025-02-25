package com.lasalle.triviaLegends.data.api.models

// Model de dades per representar una pregunta del trivial
// Utilitzem @SerializedName per mapejar els camps de la API amb el nostre model
import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("category")
    val category: String,
    
    @SerializedName("type")
    val type: String,
    
    @SerializedName("difficulty")
    val difficulty: String,
    
    @SerializedName("question")
    val question: String,
    
    @SerializedName("correct_answer")
    val correctAnswer: String,
    
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
) {
    // Funció d'utilitat per obtenir totes les respostes barrejades
    fun getAllAnswers(): List<String> {
        val allAnswers = incorrectAnswers.toMutableList()
        allAnswers.add(correctAnswer)
        return allAnswers.shuffled()
    }
} 