package pl.cgwisdom.quizzandrcgw.news

import java.io.Serializable

/**
 *PL Model danych dla pojedynczego elementu nowinki, np. dla listy nowinek
 *EN Data model for single feed item, eg. for feedlist.
 *
 *PL Reprezentuje pojedynczy węzeł w feeds bazy danych firebase
 *EN Represents single node of feeds in firebase database
 *PL Świadoma denormalizacja bazy - optymalizacja pobierań postów, w szczególności danych użytkownika
 *EN Conscious database denormalization - optimalize geting post, especially user data
 */
data class NewsItem(
        var comment: String = "",
        var points: Int = 0,
        var quiz: String = "",
        var image: String = "",
        var user: String = "",
        var timeMilis: Long = 0,
        var uid: String = "",
        var respects: HashMap<String, Int> = hashMapOf()) : Serializable