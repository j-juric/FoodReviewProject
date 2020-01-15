package com.food.review

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.fragment_owner_review.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.RatingBar


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
    var mapReview:HashMap<String,ArrayList<Review>>?=HashMap()
    var staticReviws:ArrayList<Review>?= ArrayList()
    var rev1=Review(2.0f,"20200115","Super pasta, vraticu se uopet sigurno!")
    var rev2=Review(4.0f,"20200110","Baklava je bila preslatka, dobio sam secernu bolest!")
    var rev3=Review(3.5f,"20200106","Posna napolitana posto je danas badnji dan!")

    var dishes = ArrayList<Dish>(arrayOfDishes)
    var mapForSpiner:HashMap<Dish,String>?= HashMap()

    var dishNames=ArrayList<String>(dishes.size)

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

        staticReviws!!.add(rev1)
        staticReviws!!.add(rev2)
        staticReviws!!.add(rev3)

        for(i:Dish in dishes){
            mapForSpiner!!.put(i,i.name)
            dishNames.add(i.name)
        }



        spn_dishes.adapter=ArrayAdapter<String>(activity!!,android.R.layout.simple_spinner_item,dishNames!!)

        mapReview?.set("Aglio e olio",staticReviws!!)
        mapReview?.set("Alfredo tacchini",staticReviws!!)
        mapReview?.set("Amatricana",staticReviws!!)
        mapReview?.set("Apple pie",staticReviws!!)
        mapReview?.set("Arrabbiata",staticReviws!!)
        mapReview?.set("Baklava",staticReviws!!)
        mapReview?.set("Bolognese",staticReviws!!)
        mapReview?.set("Carbonara",staticReviws!!)
        mapReview?.set("Funghi",staticReviws!!)
        mapReview?.set("Napolitana",staticReviws!!)
        mapReview?.set("Nutella pancakes",staticReviws!!)
        mapReview?.set("Pesto",staticReviws!!)
        mapReview?.set("Quattro formaggi",staticReviws!!)

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
               var selectedDish=dishes[idSpinner]
               Log.d("TAG",selectedDish.name)
               stars.rating=selectedDish.grade
           }

           override fun onNothingSelected(arg0: AdapterView<*>) {
               // TODO Auto-generated method stub
           }
        })




        btn_show.setOnClickListener{

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


}
