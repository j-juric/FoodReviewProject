package com.food.review

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Spinner
import android.R.string.cancel
import android.content.DialogInterface
import java.time.LocalDateTime


class BookingFragment(val user:Customer) : Fragment() {

    var database: FirebaseDatabase?=null
    var databaseRef: DatabaseReference?=null
    var mAuth: FirebaseAuth?=null
    var arrayOfDishes: ArrayList<Dish> ?= null

    ///Reservations    Pair <String,ArrayList<Table> PAR (DATUM : LISTA STOLOVA) FORMAT DATUMA : yyyymmdd
    var reservations:ArrayList<Pair<String,ArrayList<Table>>>?=null
   // var map:HashMap<String,ArrayList<Table>>?=null



    lateinit var optionsSpinner:Spinner

    lateinit var  result:TextView
    var formate = SimpleDateFormat("dd MMM, YYYY",Locale.FRANCE)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.FRANCE)



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
        // databaseRef!!.child("Reservations").child("YYYYMMDD").child("ID_STOLA").child("dailyReservations").child("HH:mm").setValue("Ime i Prezime Coveka") za ubacivanje
        // databaseRef!!.child("Users").child("Customers").child(user.id).child("reservationTime").setValue("YYYYMMDDhhmm")
        // user.reservationTime="YYYYMMDDhhmm"    //user je customer objekat koji je dobijen preko konstruktora


        //QUERY WHICH RETURNS DATA FOR THE NEXT 14 DAYS
        var bbb=Date()
        var ppp=bbb.toString()

        var pp2=ppp.substring(4,7)
        if (pp2=="Jan")
            pp2="01"
        if (pp2=="Feb")
            pp2="02"
        var pp1="2020"+pp2+ppp.substring(8,10)
        Log.d("naaaaa",pp1)
        val last14days = databaseRef!!.child("Reservations").orderByKey()
            .startAt(pp1)    //<---------- OVDE UNESI DANASNJI DATUM
        var asd=Calendar.getInstance()

        //QUERY LISTENER

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("now123","USAO U OVO CUDO")
                reservations = ArrayList()
                if (dataSnapshot.exists()) {
                    Log.d("now123 dataSnapshot", dataSnapshot.toString())
                    for (i in dataSnapshot.children) {
                        var p = Pair(i.key!!, i.value as ArrayList<Table>)
                        Log.d("now123", i.key.toString())
                        reservations!!.add(p)
                    }
                    //OVDE MOZE DA SE DODA DRAW


                    var stolovi = arrayListOf<Table>()
                    var vremenaa = arrayListOf<String>()
                    var velicina=0
                    var datumpravi=""
                    var pravoVreme=""
                    var daLidaUpdejt=0
                    var vv= arrayListOf<ArrayList<String>>()



                    fun algoritam(datum:String) // kako od spinera da uzmem value
                    {
                        val probniDate = datum

                        val probniSize = 4
                        Log.d("testFirstString", probniSize.toString())

                        var i = 0
                        var p = true

                        while (p && i < reservations!!.size) {

                            if (probniDate == reservations!![i].first) {
                                p = false
                                Log.d("testFirstString", reservations!![i].first)
                            } else i++
                        }

                        var pesak = reservations!![i].second.toString()

                        var pesakLista = arrayListOf<String>()

                        var cunn = 0;
                        var cc = 0;
                        var pocetak: Int = 0
                        var kraj: Int
                        Log.d("ts0", "aaaa")
                        while (cunn < pesak.length) {
                            if (pesak[cunn].equals('{')) {
                                if (cc == 0) {
                                    pocetak = cunn
                                    cc++
                                }

                            }

                            if (pesak[cunn].equals('}')) {
                                kraj = cunn
                                pesakLista.add(pesak.substring(pocetak, kraj))
                                cunn++
                                cc = 0
                            }
                            cunn++
                        }

                        Log.d("ts0", "aaaa")
                        // Log.d("testPesak1", pesakLista[1])

                        //)

                        var bbb = 0
                        Log.d("testPesak1", pesakLista.size.toString())
                        while (bbb<pesakLista.size)
                        {
                            var sto:Table=Table()
                            var vrm=vremena()
                            vremenaa.clear()
                            var iii=0
                            var pp=true
                            var counter=0
                            var cnt=0
                            var pomocniString=pesakLista[bbb]
                            //Log.d("ovde", pesakLista[bbb])
                            while(pp && iii<pesakLista[bbb].length)
                            {




                                if(pomocniString[iii].equals('='))
                                {
                                    var kraj=1
                                    if (pomocniString[iii+2].equals(','))
                                    {
                                        kraj=1
                                    }
                                    else kraj=2

                                    var pomocni1=pomocniString.substring(iii+1,iii+kraj+1)
                                    //Log.d("ovdeSad", pomocni1)

                                    if (counter==0) {
                                        sto.size= pomocni1.toInt()
                                        counter++

                                    }

                                    else if (counter==1){
                                        sto.id = pomocni1.toInt()
                                        counter++
                                    }
                                    else
                                    {
                                        if (counter==2)
                                            counter++
                                        else
                                            vremenaa.add(pomocniString.substring(iii-5,iii))
                                        // Log.d("ovdeSad", pomocniString.substring(iii-5,iii))
                                    }




                                }

                                iii++
                            }
                            stolovi.add(sto)
                            vv.add(vremenaa)


                            Log.d("sto",sto.toString())
                            Log.d("stoo2",vv[bbb].toString())
                            Log.d("stoo33",vv[bbb][0])
                            bbb++
                        }
                    }
                    fun algoritamPre(s:String)
                    {
                        datumpravi=s
                        algoritam(datumpravi)
                    }
                    fun rezervisi()
                    {
                        var jj=0
                        var yy=1
                        Log.d("rezVelicina", velicina.toString())
                        Log.d("rezVreme", pravoVreme)
                        Log.d("rezDat", datumpravi)
                        Log.d("Stolovisize", stolovi.size.toString())
                        Log.d("Stolovi", stolovi.toString())
                        Log.d("vremena", vv.toString())
                        while (jj<stolovi.size && yy==1) {
                            var pomocniNiz = arrayListOf<String>()
                            pomocniNiz = vv[jj]
                            Log.d("pomocniniz", pomocniNiz.toString())
                            Log.d("trStoSize", stolovi[jj].size.toString())


                            if (stolovi[jj].size == velicina) {
                                var uu = 0
                                var hh=1

                                while (uu < pomocniNiz.size && hh == 1) {
                                    //Log.d("vremeNema", stolovi[jj].size.toString())
                                    //Log.d("pomocninizz", pomocniNiz[uu].toString())
                                    Log.d("usao ovde", "uso")
                                    if (pomocniNiz[uu].equals(pravoVreme)) {
                                        hh = 0

                                        Log.d("vremeNema", pomocniNiz[uu])
                                    }
                                uu++}
                                if (hh== 1) {
                                    // pronadjen je sto
                                    yy=0
                                    val dialog=AlertDialog.Builder(context)
                                    val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
                                    //dialog.setView(dialogView)
                                    dialog.setTitle("Success")
                                    dialog.setMessage("Your table is ready")
                                    dialog.setPositiveButton("Back",null)
                                    //  dialog.setCancelable(false)
                                    dialog.show()
                                    Log.d("stooId", stolovi[jj].id.toString())
                                    Log.d("stooVreme", pravoVreme)
                                    Log.d(
                                        "stooVreme",
                                        datumpravi + pravoVreme.substring(
                                            0,
                                            2
                                        ) + pravoVreme.substring(3, 5)
                                    )
                                    user.reservationTime=datumpravi+pravoVreme.substring(0,2)+pravoVreme.substring(3,5)
                                    Log.d("Vreme rezervacije", user.reservationTime)
                                    //databaseRef!!.child("Reservations").child(datumpravi).child(stolovi[jj].id.toString()).child("dailyReservations").child(pravoVreme).setValue(user.firstName+user.lastName)
                                    //databaseRef!!.child("Users").child("Customers").child(user.id).child("reservationTime").setValue(datumpravi+pravoVreme.substring(0,1)+pravoVreme.substring(3,4))
                                    //user.reservationTime=datumpravi+pravoVreme.substring(0,1)+pravoVreme.substring(3,4)   //user je customer objekat koji je dobijen preko konstruktora
                                }


                            }
                            jj++
                            }
                        if (yy == 1) {
                            Log.d("stooNema", "nema mesta")
                            val dialog=AlertDialog.Builder(context)
                            val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
                            //dialog.setView(dialogView)
                            dialog.setTitle("No room")
                            dialog.setMessage("Everything is fully booked for specified date,time and number of people")
                            dialog.setPositiveButton("Back",null)
                            //  dialog.setCancelable(false)
                            dialog.show()
                            //ne postoji slobodan sto
                        }
                    }


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
                                Log.d("datumm","u datetuime picker sam")

                                //Toast.makeText(context!!, "Date : " + date, Toast.LENGTH_SHORT).show()
                                //btn_Date.text=timeFormat.format(selectedDate.time)
                                btn_Date.text=date
                                Log.d("datum", date)
                                var pomocni1=date.substring(0,2) //dan
                                var pomocni2=date.substring(3,7)
                                if (pomocni2.equals("janv"))
                                    pomocni2="01"
                                Log.d("datum", pomocni1)
                                Log.d("datum", pomocni2)
                                var pomocni3=date.substring(10,14)
                                Log.d("datum", pomocni3)
                                var konacni=pomocni3+pomocni2+pomocni1
                                Log.d("konacni", konacni)
                                Log.d("datumm",konacni.toString())
                                algoritamPre(konacni)



                            },
                            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                        )
                       datePicker.datePicker.minDate=System.currentTimeMillis()
                        Log.d("timee",System.currentTimeMillis().toString())
                        var day15=8.64*10000000*15
                        datePicker.datePicker.maxDate=System.currentTimeMillis()+day15.toLong()
                       // datePicker.datePicker.maxDate=System.
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


                    optionsSpinner=inflate.findViewById(R.id.spinn_booking_numPeople)
                    result=inflate.findViewById(R.id.res_spinner)
                    val arrayOptions= arrayListOf<Int>()
                    for(i in 1..6){

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
                            //Toast.makeText(context!!, "position : " + position, Toast.LENGTH_SHORT).show()
                            Log.d("booking",arrayOptions[position].toString())
                            velicina=arrayOptions[position]
                            Log.d("brojLjudi",arrayOptions[position].toString())
                            //result.text=arrayOptions[position].toString()
                            result.text="Time of the day:"





                        }
                    }

                    optionsSpinner=inflate.findViewById(R.id.spinn_time)
                    result=inflate.findViewById(R.id.res_spinnerr)
                    val arrayOptionss= arrayListOf<String>()
                    arrayOptionss.add("12:00")
                    arrayOptionss.add("14:00")
                    arrayOptionss.add("16:00")
                    arrayOptionss.add("18:00")
                    arrayOptionss.add("20:00")
                    arrayOptionss.add("22:00")

                    //Log.d("booking br",arrayOptions.toString())
                    optionsSpinner.adapter=ArrayAdapter<String>(context!!,android.R.layout.simple_spinner_item,arrayOptionss)
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
                            Log.d("booking",arrayOptionss[position].toString())

                            pravoVreme=arrayOptionss[position].toString()
                            //pravoVreme="13:00"
                            Log.d("vreme",pravoVreme)
                            result.text="Time of the day:"

                        }
                    }




                    val btnMyReservation=inflate.findViewById<Button>(R.id.btn_booking_btnMyReservation)
                    btnMyReservation.setOnClickListener {

                        val dialog=AlertDialog.Builder(context)
                        val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
                        //dialog.setView(dialogView)
                        dialog.setTitle("My reservation")
                        dialog.setPositiveButton("Back",null)
                        //user.reservationTime="202001211400"
                        if (user.reservationTime=="")
                        dialog.setMessage("You dont have reservation")
                        else
                        {
                            var pom=user.reservationTime.substring(4,6) + "/" + user.reservationTime.substring(6,8)
                            var pom1=user.reservationTime.substring(8,10) +"-" + user.reservationTime.substring(10,12)
                            dialog.setMessage("Date: " + pom + "\n" + "Time: " + pom1)

                            dialog.setNegativeButton("Cancel reservation",
                                DialogInterface.OnClickListener { dialog, which ->

                                    Log.d("diacancel","asd")
                                })
                        }

                        //  dialog.setCancelable(false)
                        dialog.show()
                        //val newPage= Intent(context,my_booking::class.java)
                        //startActivity(newPage)
                    }

                    val btnBook=inflate.findViewById<Button>(R.id.btn_booking_book)
                    btnBook.setOnClickListener {
                       if (velicina!=0 && pravoVreme!="" && datumpravi!="") {
                           rezervisi()

                       }
                        else{
                        val dialog=AlertDialog.Builder(context)
                        val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
                        //dialog.setView(dialogView)
                        dialog.setTitle("No date found")
                        dialog.setMessage("Please specify date")
                        dialog.setPositiveButton("Back",null)
                        //  dialog.setCancelable(false)
                        dialog.show()}
                      //val intent= Intent(activity, OwnerBookingFragment::class.java)

                       // startActivity(intent)

                    }
                } //iznad ovog

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
        return inflate
    }
}

class vremena {
    var lista = arrayListOf<String>()

}
