package com.food.review

import android.annotation.TargetApi
import android.os.Build
import java.sql.Date
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

@TargetApi(Build.VERSION_CODES.O)
data class Table(var tableSize:Int=2
                 , var id:Int=0
                 ,var dailyReservations:HashMap<String,String>?=null)
{
    constructor():this(2)
}