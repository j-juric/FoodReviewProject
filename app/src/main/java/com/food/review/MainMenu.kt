package com.food.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainMenu : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //var bottomNav:BottomNavigationView=findViewById(R.id.bottom_navigation)
        var nav_view=findViewById(R.id.bottom_navigation) as BottomNavigationView
        nav_view!!.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            HomeFragment()
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment:Fragment?=null
        when (item.itemId) {
            R.id.nav_home -> {
                selectedFragment= HomeFragment()
                setTitle("Home")
            }
            R.id.nav_reviews -> {
                selectedFragment= ReviewFragment()
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
