package com.food.review

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_booking.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class BookingFragment(val user:Customer) : Fragment() {

    var database: FirebaseDatabase?=null
    var databaseRef: DatabaseReference?=null
    var mAuth: FirebaseAuth?=null
    var arrayOfDishes: ArrayList<Dish> ?= null

    ///Reservations    Pair <String,ArrayList<Table> PAR (DATUM : LISTA STOLOVA) FORMAT DATUMA : yyyymmdd
    var reservations:ArrayList<Pair<String,ArrayList<Table>>>?=null
    var map=HashMap<String,ArrayList<Table>>()


    lateinit var optionsSpinner:Spinner
    lateinit var  result:TextView
    var formate = SimpleDateFormat("dd MMM, YYYY",Locale.FRANCE)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.FRANCE)

    var tagg="TAGG"

    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
        val inflate= inflater.inflate(R.layout.fragment_booking,container,false)

       // val txt=inflate.findViewById<TextView>(R.id.proba)
        //txt.text="jeej"


        //DATABASE_VARIABLES_START
        mAuth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference
        //DATABASE_VARIABLES_END



        /// Upis u bazu ubaciti gde treba
        // databaseRef!!.child("Reservations").child("YYYYMMDD").child("ID_STOLA").child("dailyReservations").child("HH:mm").setValue("Ime i Prezime Coveka")
        // databaseRef!!.child("Users").child("Customers").child(user.id).child("reservationTime").setValue("YYYYMMDDhhmm")
        // user.reservationTime="YYYYMMDDhhmm"    //user je customer objekat koji je dobijen preko konstruktora


        //QUERY WHICH RETURNS DATA FOR THE NEXT 14 DAYS
        val last14days = databaseRef!!.child("Reservations").endAt("20200115") //<---------- OVDE UNESI DANASNJI DATUM
        //QUERY LISTENER
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d(tagg,"USAO U OVO CUDO")
                Log.d(tagg,dataSnapshot.toString())

                if (dataSnapshot.exists()) {
                    for(i in dataSnapshot.children){
                        var lst = ArrayList<Table>()
                        for(j in i.children){
                            var t = j.getValue(Table::class.java)
                            lst.add(t!!)
                        }
                        map!!.set(i.key!!,lst)
                    }
                    Log.d(tagg,dataSnapshot.toString())

                    Log.d(tagg,"VELIKA MAPA")
                    Log.d(tagg,map.toString())
                    Log.d(tagg,"VELIKA MAPA KRAJ")

                    //map=dataSnapshot.getValue(HashMap<String,ArrayList<Table>>::class.java)
//                    for(i in dataSnapshot.children){
//                        var dish = i.getValue(Dish::class.java)
//                        map = dataSnapshot
//                        var p = Pair(i.key!!,i.value as ArrayList<Table>)
//                        Log.d(tag,i.key.toString())
//                        reservations!!.add(p)
//                    }
                    //OVDE MOZE DA SE DODA DRAW
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        //SET LISTENER
        last14days.addValueEventListener(postListener)
        ////////////////////////////////////////////////////////////////////

        //region btnDate
        val btn_Date=inflate.findViewById<TextView>(R.id.btn_booking_date)
        btn_Date.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = formate.format(selectedDate.time)
                    //Toast.makeText(context!!, "Date : " + date, Toast.LENGTH_SHORT).show()
                    //btn_Date.text=timeFormat.format(selectedDate.time)
                    btn_Date.text=date

                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
            try {
                //if (btn_Date.text != "Chose date") {
                    val date = timeFormat.parse(btn_Date.text.toString())
                    now.time = date
               // }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
//endregion

        //region btnTame
        val btn_Time=inflate.findViewById<TextView>(R.id.btn_booking_time)
        btn_Time.setOnClickListener {

            val now = Calendar.getInstance()

            val timePicker = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                btn_Time.text = timeFormat.format(selectedTime.time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show()


            /*try {
                if(btn_Time.text != "Chose time") {
                    val date = timeFormat.parse(btn_Time.text.toString())
                    now.time = date
                }
            }catch (e:Exception){
                e.printStackTrace()
            }*/
        }



        //endregion

        //region Spinner

        optionsSpinner=inflate.findViewById(R.id.spinn_booking_numPeople)
        result=inflate.findViewById(R.id.res_spinner)
        val arrayOptions= arrayListOf<Int>()
        for(i in 1..25){

            arrayOptions+=i
        }
        Log.d("booking br",arrayOptions.toString())
        optionsSpinner.adapter=ArrayAdapter<Int>(context!!,android.R.layout.simple_spinner_item,arrayOptions)
        optionsSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(context!!, "position : " + position, Toast.LENGTH_SHORT).show()
                Log.d("booking",arrayOptions[position].toString())
                result.text=arrayOptions[position].toString()
                   }
        }
        //endregion



        val btnMyReservation=inflate.findViewById<Button>(R.id.btn_booking_btnMyReservation);






        return inflate
    }
}