package com.food.review

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

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
class OwnerStatisticsFragment(val arrayOfDishes: ArrayList<Dish>) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var barChart: BarChart? = null
    val tagg = "TAGG"

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


        barChart=v.findViewById<BarChart>(R.id.chart)

        barChart!!.setTouchEnabled(false)
        barChart!!.setDrawValueAboveBar(true)

        barChart!!.setPinchZoom(false)




        var reviews=ArrayList<Review>()

        btn_top5c.setOnClickListener{
            var arr = ArrayList<Dish>(arrayOfDishes)
            var res = ArrayList<BarEntry>()
            var dish = ArrayList<String>()

            for (i in 0..4){
                Log.d(tagg,"ITERATION "+i.toString())
                var max = arr[0].numOfRev
                var ind = 0
                for (j in 1..arr.size-1){
                    if(arr[j].numOfRev > max ){
                        max = arr[j].numOfRev
                        ind = j
                    }
                }
                res.add(BarEntry(i.toFloat()+1,max.toFloat()))
                Log.d(tagg,"name "+arr[ind].name)
                Log.d(tagg,"Max "+ max)
                Log.d(tagg,"Index "+ind.toString())
                dish.add(arr[ind].name)
                arr.removeAt(ind)
            }
            Log.d(tagg,res.toString())
            Log.d(tagg,dish.toString())
            val bardataset =  BarDataSet(res!!, "")
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS,100)
            barChart!!.animateY(1000)
            var data  = BarData(bardataset)

            barChart!!.xAxis.valueFormatter=IndexAxisValueFormatter(dish)
            barChart!!.xAxis.setGranularity(1f);
            barChart!!.xAxis.setDrawLabels(true)
            barChart!!.xAxis.setDrawGridLines(false)
            barChart!!.xAxis.textSize=8f
            barChart!!.xAxis.setCenterAxisLabels(true)
            barChart!!.xAxis.setAvoidFirstLastClipping(true)
            barChart!!.xAxis.mAxisMaximum=5f
            barChart!!.xAxis.mAxisMinimum=5f
            barChart!!.xAxis.mLabelWidth=5
            barChart!!.xAxis.setGranularityEnabled(true);
            barChart!!.data=data
            barChart!!.invalidate()
        }

        btn_top5r.setOnClickListener{
            var arr = ArrayList<Dish>(arrayOfDishes)
            var res = ArrayList<BarEntry>()
            var dish = ArrayList<String>()

            for (i in 0..4){
                Log.d(tagg,"ITERATION "+i.toString())
                var max = arr[0].grade
                var ind = 0
                for (j in 1..arr.size-1){
                    if(arr[j].grade > max ){
                        max = arr[j].grade
                        ind = j
                    }
                }
                res.add(BarEntry(i.toFloat()+1,max.toFloat()))
                Log.d(tagg,"name "+arr[ind].name)
                Log.d(tagg,"Max "+ max)
                Log.d(tagg,"Index "+ind.toString())
                dish.add(arr[ind].name)
                arr.removeAt(ind)
            }
            Log.d(tagg,res.toString())
            Log.d(tagg,dish.toString())
            val bardataset =  BarDataSet(res!!, "")
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS,100)
            barChart!!.animateY(1000)
            var data  = BarData(bardataset)

            barChart!!.xAxis.valueFormatter=IndexAxisValueFormatter(dish)
            barChart!!.xAxis.setGranularity(1f);
            barChart!!.xAxis.setDrawLabels(true)
            barChart!!.xAxis.setDrawGridLines(false)
            barChart!!.xAxis.textSize=8f
            barChart!!.xAxis.setCenterAxisLabels(true)
            barChart!!.xAxis.setAvoidFirstLastClipping(true)
            barChart!!.xAxis.mAxisMaximum=5f
            barChart!!.xAxis.mAxisMinimum=5f
            barChart!!.xAxis.mLabelWidth=5
            barChart!!.xAxis.setGranularityEnabled(true);
            barChart!!.data=data
            barChart!!.invalidate()
        }

        btn_bot5c.setOnClickListener{
            var arr = ArrayList<Dish>(arrayOfDishes)
            var res = ArrayList<BarEntry>()
            var dish = ArrayList<String>()

            for (i in 0..4){
                Log.d(tagg,"ITERATION "+i.toString())
                var min = arr[0].numOfRev
                var ind = 0
                for (j in 1..arr.size-1){
                    if(arr[j].numOfRev < min ){
                        min = arr[j].numOfRev
                        ind = j
                    }
                }
                res.add(BarEntry(i.toFloat()+1,min.toFloat()))
                Log.d(tagg,"name "+arr[ind].name)
                Log.d(tagg,"Max "+ min)
                Log.d(tagg,"Index "+ind.toString())
                dish.add(arr[ind].name)
                arr.removeAt(ind)
            }
            Log.d(tagg,res.toString())
            Log.d(tagg,dish.toString())
            val bardataset =  BarDataSet(res!!, "")
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS,100)
            barChart!!.animateY(1000)
            var data  = BarData(bardataset)

            barChart!!.xAxis.valueFormatter=IndexAxisValueFormatter(dish)
            barChart!!.xAxis.setGranularity(1f);
            barChart!!.xAxis.setDrawLabels(true)
            barChart!!.xAxis.setDrawGridLines(false)
            barChart!!.xAxis.textSize=8f
            barChart!!.xAxis.setCenterAxisLabels(true)
            barChart!!.xAxis.setAvoidFirstLastClipping(true)
            barChart!!.xAxis.mAxisMaximum=5f
            barChart!!.xAxis.mAxisMinimum=5f
            barChart!!.xAxis.mLabelWidth=5
            barChart!!.xAxis.setGranularityEnabled(true);
            barChart!!.data=data
            barChart!!.invalidate()
        }

        btn_bot5r.setOnClickListener{
            var arr = ArrayList<Dish>(arrayOfDishes)
            var res = ArrayList<BarEntry>()
            var dish = ArrayList<String>()

            for (i in 0..4){
                Log.d(tagg,"ITERATION "+i.toString())
                var min = arr[0].grade
                var ind = 0
                for (j in 1..arr.size-1){
                    if(arr[j].grade < min ){
                        min = arr[j].grade
                        ind = j
                    }
                }
                res.add(BarEntry(i.toFloat()+1,min.toFloat()))
                Log.d(tagg,"name "+arr[ind].name)
                Log.d(tagg,"Max "+ min)
                Log.d(tagg,"Index "+ind.toString())
                dish.add(arr[ind].name)
                arr.removeAt(ind)
            }
            Log.d(tagg,res.toString())
            Log.d(tagg,dish.toString())
            val bardataset =  BarDataSet(res!!, "")
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS,100)
            barChart!!.animateY(1000)
            var data  = BarData(bardataset)

            barChart!!.xAxis.valueFormatter=IndexAxisValueFormatter(dish)
            barChart!!.xAxis.setGranularity(1f);
            barChart!!.xAxis.setDrawLabels(true)
            barChart!!.xAxis.setDrawGridLines(false)
            barChart!!.xAxis.textSize=8f
            barChart!!.xAxis.setCenterAxisLabels(true)
            barChart!!.xAxis.setAvoidFirstLastClipping(true)
            barChart!!.xAxis.mAxisMaximum=5f
            barChart!!.xAxis.mAxisMinimum=5f
            barChart!!.xAxis.mLabelWidth=5
            barChart!!.xAxis.setGranularityEnabled(true);
            barChart!!.data=data
            barChart!!.invalidate()
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
