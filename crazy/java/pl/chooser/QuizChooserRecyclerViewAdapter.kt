package pl.cgwisdom.quizzandrcgw.chooser

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import pl.cgwisdom.quizzandrcgw.R

/**
 *PL Adapter RecyclerView okna wyboru quizu.
 *EN RecyclerView list adapter for quiz chooser screen
 *
 *PL Z ciekawszych rzeczy zwracam uwagę na ciągłe sortowanie mapy quizów(co przypięcie).
 *PL Nie za bardzo można sortować przy aktualizacji danych - to było zamierzone.
 *PL Jeśli umiesz wyciągnąć to sortowanie w elegancki sposób to wiszę Ci browarka ;)
 *EN Funny thing is, that quizMap is sorted every time viewHolder is binding.
 *EN It's not very obvious to implement sorting when database is updating - it was on purpose here.
 *EN If you able to take sorting out of adapter in any elegant way, i owe you a beer ;)
 */
class QuizChooserRecyclerViewAdapter(private val quizzesMap: HashMap<String, QuizItem>,
                                     private val onStartquizListener: QuizChooserFragment.OnStartQuizListener) : RecyclerView.Adapter<QuizChooserRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_quizitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = quizzesMap.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sorted = quizzesMap.values.toList().sortedBy { quizItem -> (quizItem.level.ordinal + quizItem.lang.ordinal * 10) }
        holder.mItem = sorted[position]

        holder.levelImageView.setImageResource(sorted[position].level.image)
        holder.langImageView.setImageResource(sorted[position].lang.image)
        holder.quizTitle.text = getDoubleLineQuizTitle(sorted, position)

        holder.mView.setOnClickListener {
            onStartquizListener.onStartQuizSelected(holder.mItem, getSingleLineQuizTitle(sorted, position))
        }

    }

    private fun getSingleLineQuizTitle(sorted: List<QuizItem>, position: Int) = "${sorted[position].lang.getString()} \n ${sorted[position].level.getString()}"

    private fun getDoubleLineQuizTitle(sorted: List<QuizItem>, position: Int) = "${sorted[position].lang.getString()} \n ${sorted[position].level.getString()}"

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val levelImageView = mView.findViewById<View>(R.id.levelImageView) as ImageView
        val langImageView = mView.findViewById<View>(R.id.langImageView) as ImageView
        val quizTitle = mView.findViewById<View>(R.id.quizTitle) as TextView

        lateinit var mItem: QuizItem
    }
}