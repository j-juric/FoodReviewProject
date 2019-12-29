package com.food.review

import java.time.LocalDate
import java.util.*

class recepit(price:Float, id:String, date: Date, waiter:waiter)
{
    val price:Float=price
    val id:String=id
    val date:Date=date
    val waiter:waiter=waiter

    var dishes = mutableListOf<dish>()

    fun addDish(d:dish)
    {
        dishes.add(d)
    }
    var grades = mutableListOf<Int>()

    fun addGrade(grade:Int)
    {
        grades.add(grade)
    }
}