package com.food.review


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class ProfileFragment(val user:Customer) : Fragment() {

    val tagg:String="TAGG"
    @RequiresApi(Build.VERSION_CODES.O)
    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{

       //Log.d("sanja1",profile_name_surname.text.toString())
        Log.d(tagg,user.toString())
      /*NIKOLA PISAO:
        var korisnik=customer("asd","sddd","dsd","111")
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
       var rev=review(korisnik,5.0f,Date(1,1,1),"okomenesve")
        korisnik.addReview(rev)
        val strDt = simpleDate.format(rev.date)
        Log.d("111 review+ customer", korisnik.lastName + strDt + rev.user.lastName + korisnik.reviews[0].text) //+ rev.grade.toString() + rev.date.toString() )
       */

        return inflater.inflate(R.layout.fragment_profile,container,false)
    }
}