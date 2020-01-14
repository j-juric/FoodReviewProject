package com.food.review

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.github.mikephil.charting.charts.LineChart

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OwnerStatisticsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OwnerStatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnerStatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var lineChart: LineChart? = null

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
        var v =inflater.inflate(R.layout.fragment_owner_statistics, container, false)

        val btn_top5c = v.findViewById<View>(R.id.btn_top5_count)
        val btn_top5r = v.findViewById<View>(R.id.btn_top5_rating)
        val btn_bot5c = v.findViewById<View>(R.id.btn_bottom5_count)
        val btn_bot5r = v.findViewById<View>(R.id.btn_bottom5_rating)
        val rad =  v.findViewById<RadioGroup>(R.id.radio_buttons)
        val radw = v.findViewById<RadioButton>(R.id.radio_week)
        val radm = v.findViewById<RadioButton>(R.id.radio_month)
        val rady =  v.findViewById<RadioButton>(R.id.radio_year)
        rad.check(radm.id)
        lineChart=v.findViewById<LineChart>(R.id.chart)

        lineChart!!.setTouchEnabled(false)

        rad.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup,i->
        })



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
