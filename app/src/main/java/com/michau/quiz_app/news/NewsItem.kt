package com.michau.quiz_app.news

import java.io.Serializable

data class NewsItem(
    var comment: String = "",
    var points: Int = 0,
    var quiz: String = "",
    var image: String = "",
    var user: String = "",
    var timeMills: Long = 0,
    var userId: String = "",
    var respects: HashMap<String, Int> = hashMapOf()) : Serializable

//mapa user vs like (0 albo 1), dzięki przypisaniu userów można cofac lajki