package com.food.review

import java.util.*

data class Review(var rating:Float=0f
                  , var date: String=""
                  , var comment:String="")
{
    constructor():this(0f)
}