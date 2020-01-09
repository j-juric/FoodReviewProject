package com.food.review

data class Reservations (var map:List<Reserv>?=null) { //String = timestamp (YYYYMMDD)
    constructor():this(null)
}

data class Reserv (var date:String="20200101", var  tableList:ArrayList<Table>?=null){
    constructor():this("20200101")
}