package com.michau.quiz_app.chooser

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.michau.quiz_app.QApp
import com.michau.quiz_app.R
import java.io.Serializable

enum class LanguageEnum (@StringRes val lang:Int, @DrawableRes val icon:Int){
    ANDROID(R.string.lang_android, R.drawable.ic_language_android),
    KOTLIN(R.string.lang_kotlin, R.drawable.ic_language_kotlin),
    JAVA(R.string.lang_java, R.drawable.ic_language_java);

    fun getString(): String = QApp.res.getString(this.lang)
}

enum class LevelEnum(@StringRes val label:Int, @DrawableRes val image:Int){
    EASY(R.string.level_easy, R.drawable.ic_level_easy),
    MEDIUM(R.string.level_average, R.drawable.ic_level_average),
    HARD(R.string.level_hard, R.drawable.ic_level_hard);

    fun getString(): String = QApp.res.getString(this.label)
}


data class QuizItem(
    var level: LevelEnum = LevelEnum.EASY,
    var language: LanguageEnum = LanguageEnum.ANDROID,
    var questionsSet: String = "") : Serializable

