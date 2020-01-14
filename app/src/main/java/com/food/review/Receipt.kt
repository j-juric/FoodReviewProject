package com.food.review

import java.util.*
import kotlin.collections.HashMap

data class Receipt(var bill:Float?=0f
                   , var scanned:Boolean=false
                   , var orders:HashMap<String,Int>?=null
                )
{
    constructor():this(0f)
}

