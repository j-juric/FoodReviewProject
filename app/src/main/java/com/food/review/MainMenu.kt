package com.food.review

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment


class MainMenu : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //var bottomNav:BottomNavigationView=findViewById(R.id.bottom_navigation)
        var nav_view=findViewById(R.id.bottom_navigation) as BottomNavigationView
        nav_view!!.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            HomeFragment(this)
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment:Fragment?=null
        when (item.itemId) {
            R.id.nav_home -> {
                selectedFragment= HomeFragment(this)
                setTitle("Home")
            }
            R.id.nav_reviews -> {
                selectedFragment= ReviewFragment(this)
                setTitle("Reviews")
            }
            R.id.nav_scan -> {
                setTitle("Scan")
            }
            R.id.nav_reservations -> {
                selectedFragment= BookingFragment()
                setTitle("Reservations")
            }
            R.id.nav_user -> {
                selectedFragment= ProfileFragment()
                setTitle("User")
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment!!).commit()
        return true
    }
}
