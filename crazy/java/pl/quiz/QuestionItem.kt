package pl.cgwisdom.quizzandrcgw.quiz

import java.io.Serializable

/**
 *PL Model danych dla pojedynczego pytania, np. dla pytania quizu
 *EN Data model for single question item, eg. for quiz question
 */
data class QuestionItem(
        var ask: String = "ask",
        var positive: String = "pos",
        var false1: String = "fals1",
        var false2: String = "fals2") : Serializable