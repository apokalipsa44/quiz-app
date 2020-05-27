package com.michau.quiz_app.chooser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michau.quiz_app.R

class QuizItemRecyclerViewAdapter(var quizList: HashMap<String, QuizItem>) :
    RecyclerView.Adapter<QuizItemRecyclerViewAdapter.QuizItemViewHolder>() {

    inner class QuizItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val levelImgView = itemView.findViewById<View>(R.id.levelImageView) as ImageView
        val langImgView = itemView.findViewById<View>(R.id.langImageView) as ImageView
        val quizTitle = itemView.findViewById<View>(R.id.quizTitle) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_quizitem, parent, false)
        return QuizItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: QuizItemViewHolder, position: Int) {
     val sorted=quizList.values


        holder.itemView.setOnClickListener{
            //todo
        }
    }

    lateinit var quizItem: QuizItem

}
