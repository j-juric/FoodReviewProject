package com.food.review

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputFilter
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
class RateFragment(var receipt: Receipt,val userId:String,val receiptId:String,val arrayOfDishes: ArrayList<Dish>) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var scrollView : ScrollView?=null

    private val ratingList=ArrayList<RatingBar>()
    private val commList=ArrayList<EditText>()
    private var databaseRef:DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        databaseRef= FirebaseDatabase.getInstance().reference


    }

    private var viewOfLayout: View?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout =inflater.inflate(R.layout.fragment_rate, container, false)

        draw()

        var send= viewOfLayout!!.findViewById<View>(R.id.btn_send)
        send.setOnClickListener{
            var i = 0
            for(ord in receipt.orders!!.keys){
                Log.d(tag,ord.toString())
                var r = Review(ratingList[i].rating,"20200115",commList[i].text.toString())
                var k = databaseRef!!.child("Reviews").child(ord).child(r.date).push().key
                databaseRef!!.child("Receipts").child(receiptId).child("scanned").setValue(true)
                databaseRef!!.child("Reviews").child(ord).child(r.date).child(k!!).setValue(r)
                var sum=0f
                var num=0
                for(j in 0..arrayOfDishes.size){
                    if(arrayOfDishes[j].name==ord){
                        sum=arrayOfDishes[j].sumGrade
                        num=arrayOfDishes[j].numOfRev

                        break
                    }
                }
                sum+=ratingList[i].rating
                num++
                val avg = sum/num
                databaseRef!!.child("Dishes").child(ord).child("sumGrade").setValue(sum)
                databaseRef!!.child("Dishes").child(ord).child("numOfRev").setValue(num)
                databaseRef!!.child("Dishes").child(ord).child("grade").setValue(avg)

                i++
            }
        }


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
            val row = TableRow(activity)

            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                5.0f
            )

            val txtName = TextView(activity)

            txtName.textSize = 18.0F

            row.setBackgroundColor(Color.parseColor("#EEEEDD"))

            txtName.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)

            txtName.setText(d.key)
            txtName.gravity= Gravity.CENTER
            row.addView(txtName)

            val r = RatingBar(activity)
            r.numStars=5
            r.rating=0f
            r.stepSize=1f
            r.layoutParams=TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            r.scaleY=0.9f
            r.scaleX=0.9f
            ratingList.add(r)

            row.addView(r)

            val comment = EditText(activity)
            var fltr=arrayOfNulls<InputFilter>(1)
            fltr[0]= InputFilter.LengthFilter(200)
            comment.filters=fltr
            comment.layoutParams=TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 2f)
            comment.setLines(2)
            comment.maxLines=4
            comment.hint="Tell us how you liked this dish..."
            commList.add(comment)

            //commentRow.addView(comment)

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
            table.addView(comment)

            ++i
        }

        scrollView.addView(table)

    }


}
