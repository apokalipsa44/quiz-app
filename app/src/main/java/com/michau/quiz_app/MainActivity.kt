package com.michau.quiz_app

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewPager()
    }

    private fun setViewPager() {
        viewpager.adapter = getFragmentPagerAdapter()
        navigation.setOnNavigationItemSelectedListener(getBottomNavigationItemSelectedListener())
        viewpager.addOnPageChangeListener(getOnPageChangeListener())
        viewpager.offscreenPageLimit = 2
    }

    private fun getOnPageChangeListener() =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
//przy przewijaniu nic się nie dzieje - bo nie musi się dziać
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //przy przerzucaniu całej strony na bok też nic się nie dzieje
            }

            override fun onPageSelected(position: Int) {
                // przy wybieraniu strony ma się zmienic ikonka więc tu robimy
                navigation.menu.getItem(position).isChecked = true
            }
        }


    private fun getBottomNavigationItemSelectedListener() =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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
                else -> {
                    Log.wtf("Fragment out of bound", "What?")
                    return@OnNavigationItemSelectedListener false
                }
            }
        }

    private fun getFragmentPagerAdapter() =
        object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = when (position) {
                FEED_ID -> Fragment()
                CHOOSER_ID -> Fragment()
                PROFILE_ID -> Fragment()
                else -> {
                    Log.wtf("Fragment out of bound", "What?")
                    Fragment()
                }
            }

            override fun getCount(): Int {
                return 3
            }
        }

    companion object {
        const val FEED_ID = 1
        const val CHOOSER_ID = 2
        const val PROFILE_ID = 3
    }

}
