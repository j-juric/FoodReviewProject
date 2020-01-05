package com.food.review


data class User(var firstName:String =""
                , var lastName:String=""
                , var email:String=""
                , var id: String="")
{
    constructor():this("")
}