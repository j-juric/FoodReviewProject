package com.food.review

import java.util.*

class recepit(price:Float, id:String, date: Date, Waiter:Waiter)
{
    val price:Float=price
    val id:String=id
    val date:Date=date
    val Waiter:Waiter=Waiter

    var dishes = mutableListOf<Dish>()

    fun addDish(d:Dish)
    {
        dishes.add(d)
    }
    var grades = mutableListOf<Int>()

    fun addGrade(grade:Int)
    {
        grades.add(grade)
    }
}