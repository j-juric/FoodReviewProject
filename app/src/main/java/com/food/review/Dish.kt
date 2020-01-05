package com.food.review

import com.google.firebase.database.Exclude

enum class Type{
    MAIN, DESERT, SALAD, BEV
}
data class Dish(val name:String = "",
                val description:String = "",
                var grade:Float = 0f,
                var numOfRev:Int = 0,
                var sumGrade:Float = 0f,
                val type:Type = Type.MAIN)
{
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