package com.food.review

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OwnerActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OwnerReviewFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
       //toodo



          }

    val tag:String="TAGG"

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(tag,"SELECTING NAVIGATION ITEM")
        var selectedFragment: Fragment?=null
        when (item.itemId) {
            R.id.nav_statistics_owner -> {
                selectedFragment= OwnerStatisticsFragment()
                setTitle("Statistics")
            }
            R.id.nav_reviews_owner -> {
                selectedFragment= OwnerReviewFragment()
                setTitle("Reviews")
            }
            R.id.nav_reservations_owner -> {
                selectedFragment= OwnerBookingFragment()
                setTitle("Reservations")
            }

        }
        supportFragmentManager.beginTransaction().replace(R.id.owner_fragment_container,selectedFragment!!).commit()
        Log.d(tag,"FINISHED SELECTING NAVIGATION ITEM")
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("owner","usao u owner")
        setContentView(R.layout.activity_owner)
        var nav_view=findViewById<BottomNavigationView>(R.id.bottom_navigation_owner)
        nav_view.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.owner_fragment_container,
            OwnerStatisticsFragment()
        ).commit()
    }
}
