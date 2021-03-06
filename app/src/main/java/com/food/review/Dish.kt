package com.food.review


import android.widget.ImageView
import com.google.firebase.database.Exclude

enum class Type{
    MAIN, DESERT, SALAD, BEV
}
data class Dish(var name:String = "",
                var description:String = "",
                var grade:Float = 0f,
                var numOfRev:Int = 0,
                var sumGrade:Float = 0f,
                var price:Float=0f,
                val type:Type = Type.MAIN,
                var image:String="" )
{
    var myImage:ImageView?=null
    constructor():this("")
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "description" to description,
            "grade" to grade,
            "numOfRev" to numOfRev,
            "sumGrade" to sumGrade,
            "type" to type
        )
    }
    fun gradeDish(gr:Int)
    {
        numOfRev++
        sumGrade+=gr
        grade=sumGrade/numOfRev
    }
}