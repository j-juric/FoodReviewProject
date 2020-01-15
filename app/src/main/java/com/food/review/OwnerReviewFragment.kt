package com.food.review

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_owner_review.*
import android.widget.AdapterView.OnItemSelectedListener
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OwnerReviewFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OwnerReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnerReviewFragment(val arrayOfDishes: ArrayList<Dish>) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var table : TableLayout?=null

    var mapReview:HashMap<String,ArrayList<Review>>?=HashMap()

    var reviews :ArrayList<Review>?= ArrayList()

    var dishes = ArrayList<Dish>(arrayOfDishes)
    var mapForSpiner:HashMap<Dish,String>?= HashMap()
    var selectedDish:Dish?=Dish()
    var dishNames=ArrayList<String>(dishes.size)
    var databaseRef: DatabaseReference?=null



    var tagg="TAGG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_owner_review, container, false)
        val btn_show = v.findViewById<View>(R.id.show)
        var spn_dishes=v.findViewById<View>(R.id.spnDishes) as Spinner
        var stars=v.findViewById<View>(R.id.stars) as RatingBar

        databaseRef= FirebaseDatabase.getInstance().reference

        var rref = databaseRef!!.child("Reviews")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    Log.d(tag, "CITA PODATKE")
                    getRatings(dataSnapshot)

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        rref.addValueEventListener(postListener)


        for(i:Dish in dishes){
            mapForSpiner!!.put(i,i.name)
            dishNames.add(i.name)
        }



        spn_dishes.adapter=ArrayAdapter<String>(activity!!,android.R.layout.simple_spinner_item,dishNames)


        spn_dishes.setOnItemSelectedListener(object : OnItemSelectedListener {
           override fun onItemSelected(
               parent: AdapterView<*>,
               view: View,
               position: Int,
               id: Long
           ) {
               // TODO Auto-generated method stub
               var idSpinner=spn_dishes.getItemIdAtPosition(position).toInt()
               Log.d("TAG",idSpinner.toString())
               selectedDish=dishes[idSpinner]
               Log.d("TAG",selectedDish!!.name)
               stars.rating=selectedDish!!.grade
           }

           override fun onNothingSelected(arg0: AdapterView<*>) {
               // TODO Auto-generated method stub
           }
        })



        btn_show.setOnClickListener{
            Log.d("TAG",selectedDish!!.name)
            var scroll=v!!.findViewById<View>(R.id.scroll_view)
            this.table= v.findViewById(R.id.table)
            table!!.removeAllViews()
            var i:Int=0


            Log.d("TAGG",mapReview.toString())
            var id=selectedDish!!.name
            var rlist = mapReview!!.getValue(id)
            Log.d("TAGG",rlist.toString())
            rlist.forEach { d:Review->
                if(d.comment!=""){
                    var row = TableRow(activity!!)
                    row.layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        2.0f
                    )

                    var txtName = TextView(activity)
                    txtName.textSize = 23.0F
                    txtName.setTypeface(Typeface.MONOSPACE)
                    if(i%2==0)
                        row.setBackgroundColor(Color.parseColor("#43C5A5"))

                    txtName.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)
                    //val param1 = txtName.layoutParams as TableRow.LayoutParams
                    txtName.setText(d.comment)
                    txtName.gravity= Gravity.CENTER
                    row.addView(txtName)
                    this.table!!.addView(row)
                }

                i++
            }
        }



        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    fun getRatings(dataSnapshot: DataSnapshot){
        for(i in dataSnapshot.children){ //za svaki dish
            val key = i.key
            var lst = ArrayList<Review>()
            for(j in i.children){   // za svaki datum
                for(k in j.children) {    //za svaki djavo

                    Log.d(tagg,"ASDASDASD")
                    Log.d(tagg,k.value.toString())

                    var r = k.getValue(Review::class.java)

                    lst.add(r!!)
                }
            }
            mapReview!!.put(key!!,lst)
        }
        Log.d(tagg,mapReview.toString())
        Log.d(tagg,"ZAVRSIO SAM")
    }

}
