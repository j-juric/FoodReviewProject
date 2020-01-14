package com.food.review
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainMenu : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener ,
    RateFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    /////[DATABASE_VARIABLES_START]
    var database: FirebaseDatabase?=null
    var databaseRef:DatabaseReference?=null
    var mAuth: FirebaseAuth?=null
    /////[DATABASE_VARIABLES_END]

    /////[FETCHED_DATA_START]
    var arrayOfDishes: ArrayList<Dish> ?= null
    val map = HashMap<String,Int>()
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
                    connectImages(arrayOfDishes!!)
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
                selectedFragment= ScanFragment(user.id,arrayOfDishes!!)
                setTitle("Scan")
            }
            R.id.nav_reservations -> {
                selectedFragment= BookingFragment(user)
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
    fun connectImages(arr:ArrayList<Dish>){



        var string="aglio_e_olio.jpg"
        val id0 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        Log.d("TAG",id0.toString())
        Log.d("TAG",arr[0].name)
        arr[0].myImage= ImageView(this)
        //arr[0].myImage!!.setImageResource(id0)
        arr[0].myImage!!.setImageResource(id0)
        Log.d("TAG",arr[0].myImage.toString())

        string="alfredo_tacchini.jpg"
        val id1 = resources.getIdentifier("com.food.review:drawable/$string", "drawable", packageName)
        Log.d("TAG",id1.toString())
        Log.d("TAG",arr[1].name)
        arr[1].myImage= ImageView(this)
        arr[1].myImage!!.setImageResource(id1)
        Log.d("TAG",arr[1].myImage.toString())

        string="amatricana.jpg"
        val id2 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[2].myImage= ImageView(this)
        arr[2].myImage!!.setImageResource(id2)

        string="apple_pie.jpg"
        val id3 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[3].myImage= ImageView(this)
        arr[3].myImage!!.setImageResource(id3)

        string="arrabiata.jpg"
        val id4 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[4].myImage= ImageView(this)
        arr[4].myImage!!.setImageResource(id4)

        string="baklava.jpg"
        val id5 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[5].myImage= ImageView(this)
        arr[5].myImage!!.setImageResource(id5)

        string="bolognese.jpg"
        val id6 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[6].myImage= ImageView(this)
        arr[6].myImage!!.setImageResource(id6)

        string="carbonara.jpg"
        val id7 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[7].myImage= ImageView(this)
        arr[7].myImage!!.setImageResource(id7)

        string="funghi.jpg"
        val id8 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[8].myImage= ImageView(this)
        arr[8].myImage!!.setImageResource(id8)

        string="napolitana.jpg"
        val id9 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[9].myImage= ImageView(this)
        arr[9].myImage!!.setImageResource(id9)

        string="nutella_pancakes.jpg"
        val id10 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[10].myImage= ImageView(this)
        arr[10].myImage!!.setImageResource(id10)

        string="pesto.jpg"
        val id11 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[11].myImage= ImageView(this)
        arr[11].myImage!!.setImageResource(id11)

        string="quattro_formaggi.jpg"
        val id12 = resources.getIdentifier("com.food.review:drawable/$string", null, null)
        arr[12].myImage= ImageView(this)
        arr[12].myImage!!.setImageResource(id12)


    }

}
