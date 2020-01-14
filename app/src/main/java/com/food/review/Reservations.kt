package com.food.review



data class ReservationMap (var map:HashMap<String,ArrayList<Table>>?=null){
    constructor():this(null)
}