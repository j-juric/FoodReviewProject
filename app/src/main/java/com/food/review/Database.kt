package com.food.review

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference


data class Database (val mAuth:FirebaseAuth) {

    var user: FirebaseUser?=null
    var reference:DatabaseReference?=null
    fun fetchUserData(){
        user=mAuth.currentUser
    }

    fun getUserData():FirebaseUser?{
        return user
    }

}