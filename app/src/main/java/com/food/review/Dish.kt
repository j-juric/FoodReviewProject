package com.food.review

data class Dish(val name:String, val description:String, val ingred:String)
{
    enum class Type{
        MAIN, DESERT, SALAD, BEV
    }
    lateinit var type:Type
    var grade:Float=-1.0f
    var numOfRev:Float=0.0f
    var sumGrade:Float=0.0f
    var comments= mutableListOf<String>()

    fun addComment(com:String)
    {
        comments.add(com)


    }

    fun gradeDish(gr:Int)
    {
        numOfRev++
        sumGrade+=gr
        grade=sumGrade/numOfRev


    }



}