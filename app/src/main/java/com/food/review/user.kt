package com.food.review


open class user( name:String,  lastName:String,  email:String, id: String)
{
    val name:String=name
        get()=field
    val lastName:String=lastName
        get()=field
    var email:String=email
        get()=field
        set(value) {field=value}
    val id: String=id
        get()=field
    var number:Int=0
        get()=field
        set(value) {field=value}
    init{

    }
}