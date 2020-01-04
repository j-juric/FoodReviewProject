package com.food.review


open class User(firstName:String, lastName:String, email:String, id: String)
{
    val firstName:String=firstName
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