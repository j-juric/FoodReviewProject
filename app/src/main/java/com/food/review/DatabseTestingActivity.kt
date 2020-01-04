package com.food.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DatabseTestingActivity : AppCompatActivity() {

    var database:FirebaseDatabase?=null
    var databaseRef:DatabaseReference?=null
    var mAuth: FirebaseAuth?=null

    val tag:String="TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_databse_testing)
        mAuth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference
        val ref=database!!.getReference("message")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(tag, "Value is: $value")

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(tag, "Failed to read value.", error.toException())
            }
        })
    }


}
