package com.michau.quiz_app.qiuz

import java.io.Serializable

data class QuestionItem(
    var question: String = "",
    var correctAnswer: String = "",
    var falseAnswer1: String = "",
    var falseAnswer2: String = "") : Serializable