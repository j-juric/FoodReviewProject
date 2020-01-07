package com.food.review


import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.LocalDate.parse
import java.time.Month
import java.util.*
//import com.food.review.Database as
import com.google.firebase.auth.FirebaseAuth
import com.food.review.Database as FoodReviewDatabase
import com.food.review.User as User1


class ProfileFragment(val user:Customer) : Fragment() {

    val tagg:String="sanjja"
    @RequiresApi(Build.VERSION_CODES.O)
    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
       val inflate=inflater.inflate(R.layout.fragment_profile,container,false)

        var fullName:String=user.firstName+" "+user.lastName
        val txtName=inflate.findViewById<TextView>(R.id.txtFullName)
        txtName.text=fullName

        val txtEmail=inflate.findViewById<TextView>(R.id.tvEmail)
        txtEmail.text=user.email

        val txtNumber=inflate.findViewById<TextView>(R.id.tvNumber)
        txtNumber.text=user.id

        val txtPoints=inflate.findViewById<TextView>(R.id.tvPoints)
        txtPoints.text=user.credits.toString()

//region Dialog_za_ViewAllReviews

        val btnViewAll=inflate.findViewById<TextView>(R.id.tvButton)
        btnViewAll.setOnClickListener{
            val dialog=AlertDialog.Builder(context)
            val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
            //dialog.setView(dialogView)
            dialog.setTitle("All Reviews")
            dialog.setMessage("No review still :(")
            dialog.setPositiveButton("Back",null)
          //  dialog.setCancelable(false)
            dialog.show()
        }





        return inflate
    }
}