package com.food.review

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.random.Random

class OwnerActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OwnerReviewFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
       //toodo



          }

    var databaseRef: DatabaseReference?=null
    var arrayOfDishes: ArrayList<Dish> ?= null

    val tag:String="TAGG"

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(tag,"SELECTING NAVIGATION ITEM")
        var selectedFragment: Fragment?=null
        when (item.itemId) {
            R.id.nav_statistics_owner -> {
                selectedFragment= OwnerStatisticsFragment(arrayOfDishes!!)
                setTitle("Statistics")
            }
            R.id.nav_reviews_owner -> {
                selectedFragment= OwnerReviewFragment(arrayOfDishes!!)
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

        databaseRef= FirebaseDatabase.getInstance().reference
        var dref: DatabaseReference =databaseRef!!.child("Dishes")
        val context=this

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    Log.d(tag, "loadPost:onComplete")
                    getDishes(dataSnapshot)
                    var nav_view=findViewById<BottomNavigationView>(R.id.bottom_navigation_owner)
                    nav_view.setOnNavigationItemSelectedListener(context)
                    supportFragmentManager.beginTransaction().replace(R.id.owner_fragment_container,
                        OwnerStatisticsFragment(arrayOfDishes!!)
                    ).commit()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        dref.addValueEventListener(postListener)


    }

    fun getDishes(dataSnapshot:DataSnapshot){
        arrayOfDishes= ArrayList()
        for(i in dataSnapshot.children){
            var dish = i.getValue(Dish::class.java)
            arrayOfDishes!!.add(dish!!)
        }
/*
        for(i in 1..150){
            var date:String = "2019"+ Random.nextInt(10,12).toString()+ (10+i%18).toString()
            var numD= arrayOfDishes!!.size



            for(j in 1..5){
                var rate = Random.nextInt(3,6)
                var k = databaseRef!!.child("Reviews").child(arrayOfDishes!![i%numD].name).child(date!!).push().key
                var r = Review(rate.toFloat() ,date,"")
                databaseRef!!.child("Reviews").child(arrayOfDishes!![i%numD].name).child(date!!).child(k!!).setValue(r)

            }



            //databaseRef!!.child("Reservations").child(date).setValue(tableList)

        }
*/
    }
}
