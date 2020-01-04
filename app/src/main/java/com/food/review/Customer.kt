package com.food.review

class Customer(name:String, lastName:String, email:String, id:String):User(name,lastName,email,id) {
    var credit: Int = 0
        get() = field
    var reviews = mutableListOf<Review>()

    fun addReview(rev:Review)
    {
        reviews.add(rev)
    }
    fun reserve()
    {

    }

}