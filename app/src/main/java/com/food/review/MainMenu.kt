package com.food.review
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainMenu : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    /////[DATABASE_VARIABLES_START]
    var database: FirebaseDatabase?=null
    var databaseRef:DatabaseReference?=null
    var mAuth: FirebaseAuth?=null
    /////[DATABASE_VARIABLES_END]

    /////[FETCHED_DATA_START]
    var arrayOfDishes: ArrayList<Dish> ?= null
    var user:Customer=Customer()
    /////[FETCHED_DATA_END]


    val tag:String="TAGG"
    var nav_view:BottomNavigationView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        user.id=intent.getStringExtra("Uid")
        user.firstName=intent.getStringExtra("firstName")
        user.lastName=intent.getStringExtra("lastName")
        user.email=intent.getStringExtra("email")
        user.credits=intent.getIntExtra("credits",0)

        Log.d(tag,"SETTING DATABASE PARAMETERS")
        database= FirebaseDatabase.getInstance()
        Log.d(tag,"INSTANCE HAS BEEN DECLARED")
        databaseRef=FirebaseDatabase.getInstance().reference
        Log.d(tag,"DATABASE PARAMETERS HAVE BEEN SET")

        Log.d(tag,"SETTING DISH DATABASE REFERENCE")
        var dref: DatabaseReference =databaseRef!!.child("Dishes")
        Log.d(tag,"DISH DATABASE REFERENCE HAS BEEN SET")

        Log.d(tag,"SETTING CONTEXT")
        val context=this
        Log.d(tag,"CONTEXT HAS BEEN SET")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    Log.d(tag, "loadPost:onComplete")
                    getDishes(dataSnapshot)
                    nav_view=findViewById(R.id.bottom_navigation) as BottomNavigationView
                    nav_view!!.setOnNavigationItemSelectedListener(context)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        HomeFragment(context)
                    ).commit()
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        Log.d(tag,"ADDING VALUE EVENT LISTENER")
        dref.addValueEventListener(postListener)
        Log.d(tag,"VALUE EVENT LISTENER ADDED")




    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(tag,"SELECTING NAVIGATION ITEM")
        var selectedFragment:Fragment?=null
        when (item.itemId) {
            R.id.nav_home -> {
                selectedFragment= HomeFragment(this)
                setTitle("Home")
            }
            R.id.nav_reviews -> {
                selectedFragment= ReviewFragment(this,arrayOfDishes!!)
                setTitle("Reviews")
            }
            R.id.nav_scan -> {
                selectedFragment= ScanFragment()
                setTitle("Scan")
            }
            R.id.nav_reservations -> {
                selectedFragment= BookingFragment()
                setTitle("Reservations")
            }
            R.id.nav_user -> {
                selectedFragment= ProfileFragment(user)
                setTitle("User")
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment!!).commit()
        Log.d(tag,"FINISHED SELECTING NAVIGATION ITEM")
        return true
    }


    fun getDishes(dataSnapshot:DataSnapshot){
        arrayOfDishes= ArrayList()
        for(i in dataSnapshot.children){
            var dish = i.getValue(Dish::class.java)
            arrayOfDishes!!.add(dish!!)
        }
        for( i in arrayOfDishes!!){
            Log.d(tag,i.toString())
        }
    }


}
