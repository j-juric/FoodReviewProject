package com.food.review

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.images.view.*
import java.lang.invoke.MethodHandles.Lookup.PACKAGE

val PACKAGE = "com.food.review"

//arrayOfDishes je lista koja je dobijena iz baze
class ReviewFragment(val applicationContext: Context,val arrayOfDishes: ArrayList<Dish>) : Fragment() {
    var table : TableLayout?=null
    val map = HashMap<String,Int>()


private val images = listOf(R.drawable.iaglio_e_olio, R.drawable.ialfredo_tacchini, R.drawable.iamatricana,
 R.drawable.iapple_pie, R.drawable.iarrabbiata, R.drawable.ibaklava, R.drawable.ibolognese,
 R.drawable.icarbonara, R.drawable.ifunghi, R.drawable.inapolitana,
 R.drawable.inutella_pancakes, R.drawable.ipesto, R.drawable.iquattro_fromaggi)


private lateinit var viewOfLayout: View

@Nullable
override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
    viewOfLayout = inflater.inflate(R.layout.fragment_review,container,false)
 //arr.add(dish1)
 //arr.add(dish2)
initializeMap()
    draw()

 return viewOfLayout
}

fun initializeMap(){
    map.set("R.drawable.iaglio_e_olio",0)
    map.set("R.drawable.ialfredo_tacchini",1)
    map.set("R.drawable.iamatricana",2)
    map.set("R.drawable.iapple_pie",3)
    map.set("R.drawable.iarrabbiata",4)
    map.set("R.drawable.ibaklava",5)
    map.set("R.drawable.ibolognese",6)
    map.set("R.drawable.icarbonara",7)
    map.set("R.drawable.ifunghi",8)
    map.set("R.drawable.inapolitana",9)
    map.set("R.drawable.inutella_pancakes",10)
    map.set("R.drawable.ipesto",11)
    map.set("R.drawable.iquattro_fromaggi",12)
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



     val r = RatingBar(activity)
     r.isClickable=false
     r.numStars=5
     r.rating=d.grade
     r.stepSize=0.1f
     r.layoutParams=TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
     r.scaleY=0.5f
     r.scaleX=0.5f
     row.addView(r)


     var strForMap="R.drawable.i"+d.image
     Log.d("TAG", strForMap)
       row.setOnClickListener{
         val builder = AlertDialog.Builder(applicationContext)
         var dialogView=layoutInflater.inflate(R.layout.images,null)
         var showImage=dialogView.findViewById<ImageView>(R.id.iarrabbiata)


         showImage.setImageResource(images[map.get(strForMap)!!])

         //set title for alert dialog
         builder.setTitle(d.name)
         //set message for alert dialog

         builder.setMessage(d.description)
         builder.setIcon(android.R.drawable.ic_dialog_info)

         Log.d("TAG",d.myImage.toString())
         builder.setView(dialogView)

         builder.setPositiveButton("Close"){dialogInterface, which ->
             Toast.makeText(applicationContext,"closed",Toast.LENGTH_LONG).show()
         }

         // Create the AlertDialog
         val alertDialog: AlertDialog = builder.create()
         // Set other dialog properties
         alertDialog.setCancelable(false)
         alertDialog.show()

     }
     this.table!!.addView(row)

     ++i}
 }
private fun getDrawable(name: String): Int {
 return getId(name, "drawable")
}
private fun getId(name: String, type: String): Int {
 return resources.getIdentifier(name, type, com.food.review.PACKAGE)
}

}