package com.food.review

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_rate.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RateFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RateFragment(var receipt: Receipt,val userId:String,val receiptId:String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var scrollView : ScrollView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var viewOfLayout: View?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout =inflater.inflate(R.layout.fragment_rate, container, false)

        draw()


        return viewOfLayout
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    fun draw(){
        val scrollView = viewOfLayout!!.findViewById<ScrollView>(R.id.scroll_view)
        val table= TableLayout(activity)

        var i:Int=0
        receipt.orders!!.forEach { d ->
            var row = TableRow(activity)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                2.0f
            )

            var txtName = TextView(activity)
            txtName.textSize = 24.0F
            if(i%2==0)
                row.setBackgroundColor(Color.parseColor("#EEEEDD"))

            txtName.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)

            txtName.setText(d.key)
            txtName.gravity= Gravity.CENTER
            row.addView(txtName)

            var txtGrade = TextView(activity)
            txtGrade.textSize = 24.0F
            txtGrade.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)

            txtGrade.setText(d.value.toString())
            txtGrade.gravity= Gravity.RIGHT
            row.addView(txtGrade)

            /*
            row.setOnClickListener{
                val builder = AlertDialog.Builder(activity)
                //set title for alert dialog
                builder.setTitle(d.name)
                //set message for alert dialog
                builder.setMessage(d.description)
                builder.setIcon(android.R.drawable.ic_dialog_info)


                builder.setPositiveButton("Close"){dialogInterface, which ->
                    Toast.makeText(activity,"closed", Toast.LENGTH_LONG).show()
                }

                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()

            }
            */


            table.addView(row)

            ++i
        }

        scrollView.addView(table)

    }


}
