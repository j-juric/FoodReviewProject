package com.food.review

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OwnerBookingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OwnerBookingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnerBookingFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var table : TableLayout?=null




    // citanje iz baze customera sa rezervacijama
    var ljudi= arrayListOf<Customer>()

    private lateinit var viewOfLayout: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("owner","usoo")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout = inflater.inflate(R.layout.fragment_owner_booking,container,false) //inflater.inflate(R.layout.fragment_owner_statistics, container, false)
        //arr.add(dish1)
        //arr.add(dish2)
        draw()

        return viewOfLayout



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_owner_booking, container, false)
    }
    fun draw(){
        var covek1=Customer()
        covek1.reservationTime="20200121"
        covek1.firstName="Rayn"
        covek1.lastName="Edvards"
        var covek2=Customer()
        covek2.reservationTime="20211121"
        covek2.firstName="Alicia"
        covek2.lastName="Dawn"
        var covek3=Customer()
        covek3.reservationTime="20222121"
        covek3.firstName="Katrin"
        covek3.lastName="Jhones"
        ljudi.add(covek1)
        ljudi.add(covek2)
        ljudi.add(covek3)
        Log.d("asaa",ljudi.toString())
        //this.table= viewOfLayout.findViewById(R.id.tabela_booking)
        var i:Int=0
        ljudi!!.forEach { d ->
            var row = TableRow(this.context)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                2.0f
            )

            var txtName = TextView(this.context)
            txtName.textSize = 24.0F
            if(i%2==0)
                row.setBackgroundColor(Color.parseColor("#43C5A5"))

            txtName.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)
            val param1 = txtName.layoutParams as TableRow.LayoutParams
            txtName.setText(d.firstName+" "+d.lastName)
            txtName.gravity= Gravity.CENTER
            row.addView(txtName)

            var txtGrade = TextView(this.context)
            txtGrade.textSize = 24.0F
            txtGrade.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)
            val param = txtGrade.layoutParams as TableRow.LayoutParams
            txtGrade.setText(d.reservationTime)
            txtGrade.gravity= Gravity.RIGHT
            row.addView(txtGrade)


            row.setOnClickListener{
                val builder = AlertDialog.Builder(this.context)
                //set title for alert dialog
                builder.setTitle("Delete reservation")
                //set message for alert dialog
                builder.setMessage("Are you sure you want to remove this reservation?")
                //builder.setIcon(android.R.drawable.ic_dialog_info)


                builder.setPositiveButton("Close"){dialogInterface, which ->
                    Toast.makeText(this.context,"closed", Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("Delete reservation",
                    DialogInterface.OnClickListener { builder, which ->

                        Log.d("diacancel","asd")
                        // da obrise rezervaciju koju ima user d
                        // d.reservationTime d.id
                        //JURICA TODO0
                    })


                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
            //this.table!!.addView(row)

            ++i
        }}

    // TOoDO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    interface OnFragmentInteractionListener {
        // ToODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
