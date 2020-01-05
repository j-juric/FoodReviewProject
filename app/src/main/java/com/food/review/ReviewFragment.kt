package com.food.review

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

//arrayOfDishes je lista koja je dobijena iz baze
class ReviewFragment(val applicationContext: Context,val arrayOfDishes: ArrayList<Dish>) : Fragment() {
    var table : TableLayout?=null

    private lateinit var viewOfLayout: View

    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
        viewOfLayout = inflater.inflate(R.layout.fragment_review,container,false)
        //arr.add(dish1)
        //arr.add(dish2)
        draw()

        return viewOfLayout
    }

    fun draw(){
        this.table= viewOfLayout.findViewById(R.id.main_table)
        var i:Int=0
        arrayOfDishes!!.forEach { d ->
            var row = TableRow(applicationContext)
            row.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                2.0f
            )

            var txtName = TextView(applicationContext)
            txtName.textSize = 24.0F
            if(i%2==0)
                row.setBackgroundColor(Color.parseColor("#EEEEDD"))

            txtName.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f)
            val param1 = txtName.layoutParams as TableRow.LayoutParams

            txtName.setText(d.name)

            txtName.gravity= Gravity.CENTER
            row.addView(txtName)
           /* row.setOnClickListener{object : View.OnClickListener{
                override fun onClick(v: View?) {

                }}

            }*/
            this.table!!.addView(row)

            ++i}
        }


}