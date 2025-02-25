package com.lasalle.triviaLegends.data.api.models

// Model de dades per representar una pregunta del trivial
// Utilitzem @SerializedName per mapejar els camps de la API amb el nostre model
import com.google.gson.annotations.SerializedName

/**
 * Model de dades per representar una pregunta del trivial
 * 
 * @author Pol & Teo
 * 
 * NOTA TEO: He afegit el shuffled() a getAllAnswers() per barrejar les respostes
 * cada cop que les demanem. Així evitem que la correcta estigui sempre al mateix lloc.
 * 
 * NOTA POL: Bona idea! També he posat SerializedName per si l'API canvia els noms
 * dels camps, només haurem de canviar-ho aquí.
 */
data class Question(
    // Categoria de la pregunta (p.ex: "Història", "Ciència", etc.)
    @SerializedName("category")
    val category: String,
    
    // Tipus de pregunta (multiple o boolean)
    @SerializedName("type")
    val type: String,
    
    // Nivell de dificultat (easy, medium, hard)
    @SerializedName("difficulty")
    val difficulty: String,
    
    // Text de la pregunta
    @SerializedName("question")
    val question: String,
    
    // Resposta correcta
    @SerializedName("correct_answer")
    val correctAnswer: String,
    
    // Llista de respostes incorrectes
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
) {
    /**
     * Retorna totes les respostes barrejades.
     * És important barrejar-les per no tenir la correcta sempre en la mateixa posició.
     * 
     * NOTA POL: Potser hauríem d'afegir una cache per no barrejar cada cop que cridem el mètode?
     * NOTA TEO: De moment ho deixem així, si veiem que va lent ho optimitzem.
     */
    fun getAllAnswers(): List<String> {
        val allAnswers = incorrectAnswers.toMutableList()
        allAnswers.add(correctAnswer)
        return allAnswers.shuffled()
    }
} 