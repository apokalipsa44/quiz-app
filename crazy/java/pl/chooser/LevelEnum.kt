package pl.cgwisdom.quizzandrcgw.chooser

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import pl.cgwisdom.quizzandrcgw.QApp
import pl.cgwisdom.quizzandrcgw.R

/**
 *PL Mapowanie poziomów trudności i towarzyszące im zasoby
 *EN Difficulty Levels mapping and associated resources
 */
enum class LevelEnum(@StringRes val label: Int,
                     @DrawableRes val image: Int) {
    EASY(R.string.level_easy, R.drawable.ic_level_easy),
    AVERAGE(R.string.level_average, R.drawable.ic_level_average),
    HARD(R.string.level_hard, R.drawable.ic_level_hard);

    fun getString() =
            QApp.res.getString(this.label)
}