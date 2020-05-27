package com.michau.quiz_app.chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michau.quiz_app.R
import kotlinx.android.synthetic.main.fragment_quizitem_list.*

class QuizChooserFragment : Fragment() {

    private val quizList: HashMap<String,QuizItem> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quizitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpRecyclerView()
        setUpCommunication()

    }

    private fun setUpRecyclerView() {
        quest_item_list.layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        quest_item_list.adapter = QuizItemRecyclerViewAdapter(quizList)

    }

    companion object {
        const val COLUMN_COUNT = 3
    }
}