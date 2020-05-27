package pl.cgwisdom.quizzandrcgw.chooser

import java.io.Serializable

/**
 *PL Model danych dla pojedynczego quizu, np. zestawu pytań "Java Średni"
 *EN Data model for single quiz item, eg. for question set "Java Average"
 */

data class QuizItem(
        var level: LevelEnum = LevelEnum.EASY,
        var lang: LangEnum = LangEnum.ANDROID,
        var questset: String = "") : Serializable