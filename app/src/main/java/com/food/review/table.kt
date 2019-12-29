package com.food.review

import android.annotation.TargetApi
import android.os.Build
import java.sql.Date
import java.time.LocalDate
import java.util.*

@TargetApi(Build.VERSION_CODES.O)
class table(numOfPeople:Int, id:String)
{
    var date:Date=Date(0,0,0)
    // dva niza sa brojevima ili jbmliga

    val numOfPeople:Int=numOfPeople
    val id:String=id

}