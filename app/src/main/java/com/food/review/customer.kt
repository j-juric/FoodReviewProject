package com.food.review

class customer(name:String,lastName:String,email:String, id:String):user(name,lastName,email,id) {
    var credit: Int = 0
        get() = field
    var reviews = mutableListOf<review>()

    fun addReview(rev:review)
    {
        reviews.add(rev)
    }
    fun reserve()
    {

    }

}