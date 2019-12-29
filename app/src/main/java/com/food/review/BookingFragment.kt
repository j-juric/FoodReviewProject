package com.food.review

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_booking.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class BookingFragment : Fragment() {



    var formate = SimpleDateFormat("dd MMM, YYYY",Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
        val inflate= inflater.inflate(R.layout.fragment_booking,container,false)

       // val txt=inflate.findViewById<TextView>(R.id.proba)
        //txt.text="jeej"


        //region btn Date
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

//region btnDate
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

        return inflate
    }
}