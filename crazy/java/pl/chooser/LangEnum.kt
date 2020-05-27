package pl.cgwisdom.quizzandrcgw.chooser

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import pl.cgwisdom.quizzandrcgw.QApp
import pl.cgwisdom.quizzandrcgw.R

/**
 *PL Mapowanie języków i towarzyszące im zasoby
 *EN Langs mapping and associated resources
 */
enum class LangEnum(@StringRes val label: Int,
                    @DrawableRes val image: Int) {
    ANDROID(R.string.lang_android, R.drawable.ic_language_android),
    KOTLIN(R.string.lang_kotlin, R.drawable.ic_language_kotlin),
    JAVA(R.string.lang_java, R.drawable.ic_language_java);

    fun getString() =
            QApp.res.getString(this.label)
}