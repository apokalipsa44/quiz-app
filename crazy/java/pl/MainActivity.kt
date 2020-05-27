package pl.cgwisdom.quizzandrcgw

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsitem_list.*
import kotlinx.android.synthetic.main.fragment_quizitem_list.*
import pl.cgwisdom.quizzandrcgw.chooser.QuizChooserFragment
import pl.cgwisdom.quizzandrcgw.chooser.QuizItem
import pl.cgwisdom.quizzandrcgw.news.NewsItem
import pl.cgwisdom.quizzandrcgw.news.NewsListFragment
import pl.cgwisdom.quizzandrcgw.profile.OtherProfileActivity
import pl.cgwisdom.quizzandrcgw.profile.ProfileFragment
import pl.cgwisdom.quizzandrcgw.profile.UserItem
import pl.cgwisdom.quizzandrcgw.quiz.QuestionItem
import pl.cgwisdom.quizzandrcgw.quiz.QuizActivity
import pl.cgwisdom.quizzandrcgw.summary.QuizSummaryActivity
import pl.cgwisdom.quizzandrcgw.summary.QuizSummaryActivity.Companion.NEW_FEED

/**
 *PL Główna aktywność aplikacji. W niej jest ViewPager obsługiwany gestami i BottomNavigationViewem
 *EN Main activity in application. Here is gesture supported ViewPager enriched in BottomNavigationView
 */
class MainActivity : BaseActivity(),
        QuizChooserFragment.OnStartQuizListener,
        NewsListFragment.OnNewsInteractionListener,
        ProfileFragment.OnLogChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
    }

    //region Ustawienia ViewPagera i bottomNavigation
    private fun setViewPager() {
        viewpager.adapter = getFragmentPagerAdapter()
        navigation.setOnNavigationItemSelectedListener(getBottomNavigationItemSelectedListener())
        viewpager.addOnPageChangeListener(getOnPageChangeListener())
        viewpager.offscreenPageLimit = 2
    }

    private fun getFragmentPagerAdapter() =
            object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int) = when (position) {
                    FEED_ID -> NewsListFragment()
                    CHOOSER_ID -> QuizChooserFragment()
                //todo nie instancjonowac tego tak, gdy będzie internet!
                    PROFILE_ID -> ProfileFragment()
                    else -> {
                        Log.wtf("Fragment out of bounds", "How Came?!")
                        Fragment()
                    }
                }

                override fun getCount() = 3

            }

    private fun getBottomNavigationItemSelectedListener() =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        viewpager.currentItem = 0
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_dashboard -> {
                        viewpager.currentItem = 1
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_notifications -> {
                        viewpager.currentItem = 2
                        return@OnNavigationItemSelectedListener true
                    }
                    else -> false

                }
            }

    private fun getOnPageChangeListener() =
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    navigation.menu.getItem(position).isChecked = true
                }
            }


    //endregion


    //region Obsluga wynikow z okien
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when {
                (requestCode == QUIZ_ACT_REQ_CODE) -> {
                    navigateToSummaryActivity(data)
                }
                (requestCode == QUIZ_SUMMARY_RCODE) -> {
                    pushNewNews(data)
                }
            }
        }
    }

    private fun pushNewNews(data: Intent?) {
        val feedItem = data!!.extras.get(NEW_FEED) as NewsItem
        QApp.fData.getReference("feeds").push().setValue(feedItem.apply {
            uid = QApp.fUser!!.uid
            image = QApp.fUser!!.photoUrl.toString()
            user = QApp.fUser!!.displayName!!
        })
        viewpager.currentItem = 0
        getNewsListFragment().feed_item_list.smoothScrollToPosition(0)
    }

    private fun navigateToSummaryActivity(data: Intent?) {
        val intent = Intent(this, QuizSummaryActivity::class.java).apply {
            if (QApp.fUser != null) {
                data?.putExtra(USER_NAME, QApp.fUser?.displayName
                        ?: QApp.res.getString(R.string.anonym_name))
                data?.putExtra(USER_URL, QApp.fUser?.photoUrl.toString())
            }
            putExtras(data!!.extras)
        }
        startActivityForResult(intent, QUIZ_SUMMARY_RCODE)
    }
    //endregion

    //region Obsluga interfejsow z fragmentow
    private fun getChooserListFragment() =
            (supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + CHOOSER_ID) as QuizChooserFragment)

    private fun getNewsListFragment() =
            (supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + FEED_ID) as NewsListFragment)

    override fun onStartQuizSelected(quiz: QuizItem, name: String) {
        getChooserListFragment().loader_quiz.visibility = View.VISIBLE

        QApp.fData.getReference("questions/${quiz.questset}").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val quizset = ArrayList<QuestionItem>()
                p0.children.map { it.getValue(QuestionItem::class.java) }.mapTo(quizset) { it!! }
                getChooserListFragment().loader_quiz.visibility = View.GONE
                navigateQuiz(quizset, name, quiz)
            }
        })
    }

    fun navigateQuiz(quizSet: ArrayList<QuestionItem>, title: String, quiz: QuizItem) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QUIZ_SET, quizSet)
            putExtra(TITLE, title)
            putExtra(QUIZ, quiz)
        }
        startActivityForResult(intent, QUIZ_ACT_REQ_CODE)
    }


    override fun onUserSelected(user: UserItem, image: View) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        intent.putExtra(USER_ITEM, user)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image, "circleProfileImageTransition")
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onLikeSelected(feedId: String, diff: Int) {
        if (QApp.fUser != null) {
            QApp.fData.getReference("feeds/$feedId/respects").updateChildren(mapOf(Pair(QApp.fUser?.uid, diff)))
                    .addOnCompleteListener { Log.d("MainActivity", "Just liked $feedId, with $diff") }
        }
    }

    override fun onLogout() {
        QApp.fAuth.signOut()
        getNewsListFragment().feed_item_list.adapter.notifyDataSetChanged()
    }

    override fun onLogIn() {
        logIn()
    }

    //endregion
    companion object {
        const val FEED_ID = 0
        const val CHOOSER_ID = 1
        const val PROFILE_ID = 2

        const val QUIZ_SET = "quiz_set"
        const val TITLE = "TITLE"
        const val QUIZ = "QUIZ"

        const val USER_ITEM = "USER_ITEM"

        const val USER_NAME = "USER_NAME"
        const val USER_URL = "USER_URL"

        const val QUIZ_ACT_REQ_CODE = 324
        const val QUIZ_SUMMARY_RCODE = 2431
    }
}
