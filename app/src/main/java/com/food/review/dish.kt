package com.food.review

class dish(name:String,description:String,ingred:String)
{
    var name:String=name
    val description:String=description
    var ingred:String=ingred

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