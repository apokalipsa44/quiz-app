package pl.cgwisdom.quizzandrcgw.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_profile.*
import pl.cgwisdom.quizzandrcgw.BaseActivity
import pl.cgwisdom.quizzandrcgw.MainActivity
import pl.cgwisdom.quizzandrcgw.QApp
import pl.cgwisdom.quizzandrcgw.R
import pl.cgwisdom.quizzandrcgw.news.NewsListFragment

/**
 *PL Aktywność odpowiedzialna za prezentowanie obcego profilu użytkownika
 *EN Activity responsible for presenting other user profile
 */
class OtherProfileActivity : BaseActivity(),
        NewsListFragment.OnNewsInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent?.extras?.get(MainActivity.USER_ITEM) as UserItem
        setContentView(R.layout.other_profile_activity)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.layout_other_profile, ProfileFragment.newInstance(user)).commit()
    }

    override fun onStart() {
        super.onStart()
        setUpToolbar()
    }

    /**
     * Dostosowuje fragment profili pod obcy profil. Prawdopodobnie do refaktoru, ale jak się wyklaruje okno
     * Nie bierz tej metody na poważnie, we fragmencie narobiłoby niezłego szamba
     * a wystawianie kolejnych interfejsów tam sobie darujmy
     */
    private fun setUpToolbar() {
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onUserSelected(user: UserItem, image: View) {
        //niedostepne na tym oknie
    }

    override fun onLikeSelected(feedId: String, diff: Int) {
        if (QApp.fUser != null) {
            QApp.fData.getReference("feeds/$feedId/respects").updateChildren(mapOf(Pair(QApp.fUser?.uid, diff)))
                    .addOnCompleteListener { Log.d("MainActivity", "Just liked $feedId, with $diff") }
        }
    }
}