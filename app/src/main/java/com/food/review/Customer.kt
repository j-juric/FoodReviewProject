package com.food.review

data class Customer( var firstName:String=""
                    ,  var lastName:String=""
                    , var email:String=""
                    , var id:String=""
                    ,var credits: Int=0) {
    constructor():this("")

}