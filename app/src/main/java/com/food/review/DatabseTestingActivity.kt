package com.food.review

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder

import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_databse_testing.*
import com.google.zxing.WriterException

import android.graphics.Point

import android.view.WindowManager

import android.graphics.Bitmap
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random


class DatabseTestingActivity : AppCompatActivity() {

    var database:FirebaseDatabase?=null
    var databaseRef:DatabaseReference?=null
    var mAuth: FirebaseAuth?=null
    var arrayOfDishes: ArrayList<Dish> ?= null
    var arrayOfReceipt: ArrayList<Receipt> ?=null

    var qrImage:ImageView ?=null
    var bitmap: Bitmap? = null
    var receiptList:ArrayList<String> = ArrayList<String>()

    var btn_gen : Button?=null

    var itr=1


    var reservations:ArrayList<Pair<String,ArrayList<Table>>>?=null

    val tag:String="TAGG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_databse_testing)
        mAuth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference

        Log.d(tag,"OTVORI SE SEZAME")
        qrImage=this.QR_Image as ImageView
        btn_gen=this.btn_generate as Button

        //QUERY WHICH RETURNS DATA FOR THE NEXT 14 DAYS
//        val last14days = databaseRef!!.child("Reservations").orderByKey()
//            .startAt("20200120")
//        //QUERY LISTENER
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                reservations = ArrayList()
//                if (dataSnapshot.exists()) {
//                    Log.d(tag,dataSnapshot.toString())
//                    for(i in dataSnapshot.children){
//                        var p = Pair(i.key!!,i.value as ArrayList<Table>)
//                        Log.d(tag,i.key.toString())
//                        reservations!!.add(p)
//                    }
//                    //OVDE MOZE DA SE DODA DRAW AKO TREBA
//                }
//
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        }
//        //SET LISTENER
//        last14days.addValueEventListener(postListener)

        val inputValue = "-LyFeDi8UN_KrSA8BuaU".trim()

        receiptList.add("-Lyan7GGTSdEL2ZaDttP")
        receiptList.add("-Lyan7GKDZDLhzEpvw5r")
        receiptList.add("-Lyan7GLZzlmknzxIZUj")
        receiptList.add("-Lyan7GMgw2_zisxnazX")
        receiptList.add("-Lyan7GNUCFBfl29s4fK")
        receiptList.add("-Lyan7GO1_FvvnA3QuZl")
        receiptList.add("-Lyan7GPQ9vZTZT5tlj8")
        receiptList.add("-Lyan7GSGOFX5pigF6x7")
        receiptList.add("-Lyan7GtEJ_HcDdp9Ohp")

        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)

        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        var qrgEncoder = QRGEncoder(
            receiptList[0], null,
            QRGContents.Type.TEXT,
            smallerDimension
        )
        try {
            bitmap = qrgEncoder.bitmap
            qrImage!!.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.d(tag, "GRESKA")
        }

        btn_gen!!.setOnClickListener {
            generateCode()
        }


//        val key = databaseRef!!.child("Receipts").push().key
//
//        var timeList:ArrayList<String> = ArrayList<String>(6)
//
//        timeList.add("12:00")
//        timeList.add("14:00")
//        timeList.add("16:00")
//        timeList.add("18:00")
//        timeList.add("20:00")
//        timeList.add("22:00")
//
//        var nameList:ArrayList<String> = ArrayList<String>(12)
//        nameList.add("Kolumbo")
//        nameList.add("Dubravka")
//        nameList.add("Trifun")
//        nameList.add("Svetozar")
//        nameList.add("Jefimije")
//        nameList.add("Pero")
//        nameList.add("Milo")
//        nameList.add("Ratislav")
//        nameList.add("Ratimir")
//        nameList.add("Svemirko")
//        nameList.add("Josif")
//        nameList.add("Ratko")


//        var r = ReservationMap()
//
//
//        r.map=HashMap<String,ArrayList<Table>>()
//        for(i in 1..14){
//            var date:String = "202001"+ (15+i).toString()
//            Log.d(tag,"AAA")
//            var tableList:ArrayList<Table> = ArrayList<Table>(20)
//            Log.d(tag,"BBB")
//            var maps:ArrayList<HashMap<String,String>> = ArrayList<HashMap<String,String>>()
//            for (kk in 1..20){
//                var map  = HashMap<String,String>()
//                for(k in 0..3){
//                    map.put(timeList[k],nameList[Random.nextInt(0,11)])
//                }
//                maps.add(map)
//            }
//            Log.d(tag,"CCC")
//            for(j in 0..19){
//                var table:Table= Table(Random.nextInt(1,4)*2,j,maps[j])
//                tableList.add(table)
//            }
//            r.map!!.put(date,tableList!!)
//            Log.d(tag,"DDD")
//            //databaseRef!!.child("Reservations").child(date).setValue(tableList)
//
//        }
//        databaseRef!!.child("Reservations").setValue(r.map!!)







//        arrayOfDishes= ArrayList()
//        databaseRef!!.child("Dishes").child("Carbonara").child("image").setValue("carbonara")
//        databaseRef!!.child("Dishes").child("Bolognese").child("image").setValue("bolognese")
//        databaseRef!!.child("Dishes").child("Pesto").child("image").setValue("pesto")
//        databaseRef!!.child("Dishes").child("Quattro Fromaggi").child("image").setValue("quattro_fromaggi")
//        databaseRef!!.child("Dishes").child("Aglio e olio").child("image").setValue("aglio_e_olio")
//        databaseRef!!.child("Dishes").child("Napolitana").child("image").setValue("napolitana")
//        databaseRef!!.child("Dishes").child("Alfredo Tacchini").child("image").setValue("alfredo_tacchini")
//        databaseRef!!.child("Dishes").child("Amatriciana").child("image").setValue("amatriciana")
//        databaseRef!!.child("Dishes").child("Funghi").child("image").setValue("funghi")
//        databaseRef!!.child("Dishes").child("Arrabbiata").child("image").setValue("arrabbiata")
//        databaseRef!!.child("Dishes").child("Nutella Pancakes").child("image").setValue("nutella_pancakes")
//        databaseRef!!.child("Dishes").child("Baklava").child("image").setValue("baklava")
//        databaseRef!!.child("Dishes").child("Apple Pie").child("image").setValue("apple_pie")

        /*
        var d1 = Dish("Carbonara","Italijanska pasta ciji su glavni sastojci neutralna pavlaka, jaja i slanina",0F,0,0F,30,Type.MAIN)
        var d2 = Dish("Bolognese","It consists of spaghetti served with a sauce made from tomatoes, minced beef, garlic, wine and herbs;",0F,0,0F,30,Type.MAIN)
        var d3 = Dish("Pesto","The original pesto alla genovese, the quintessential pesto recipe, is made with Genovese basil, coarse salt, garlic, Ligurian extra virgin olive oil (Taggiasco), European pine nuts (sometimes toasted) and a grated cheese like Parmigiano-Reggiano",0F,0,0F,35,Type.MAIN)
        var d4 = Dish("Quattro Fromaggi","Pasta Quattro Formaggi, which is Italian for 'Pasta with Four Cheeses'. The most commonly used cheese varieties for this recipe are parmesan, gorgonzola, fontina, and provolone or mozzarella.",0F,0,0F,50,Type.MAIN)
        var d5 = Dish("Aglio e olio","Kaže se da kada bi postojala biblija o pastama, prva rečenica bi glasila, “U početku, Bog stvori “Aglio e Olio”. Drugim rečima, način na koji se danas pripremaju paste, Aglio e Olio je skoro uvek prvi korak.",0F,0,0F,35,Type.MAIN)
        var d6 = Dish("Napolitana","Napolitana sos se još naziva i Napoli sos, odnosno Napuljski sos, a to je kolektivni naziv za svaki sos koji se bazira na umaku od paradajza, a potiče iz italijanske kuhinje.",0F,0,0F,40,Type.MAIN)
        var d7 = Dish("Alfredo Tacchini","Alfredo sos je zapravo jedan od najstarijih i najjednostavnijih načina za pripremu testenine. Italijanska verzija ovog sosa sadrži puter i parmezan, dok je američka sa pavlakom i parmezanom.",0F,0,0F,50,Type.MAIN)
        var d8 = Dish("Amatriciana","Amatriciana je tradicionalni italijanski pasta sos na bazi slanine, rendanog sira, paradajza i crnog luka. Poreklom je iz grada Amatrice i jedan je od najpoznatijih soseva za testenine rimske, odnosno italijanske kuhinje.",0F,0,0F,60,Type.MAIN)
        var d9 = Dish("Funghi","Postoji puno recepta za sos sa pečurkama, ali ono što čini ovu pastu posebnom jeste savršen spoj sa pavlakom, belim lukom i parmezanom. Funghi sos za pastu je kremas, primamljv i divnog ukusa pa je sasvim opravdano jedan od najtraženijih u Pasta baru 2×2.",0F,0,0F,45,Type.MAIN)
        var d10 = Dish("Arrabbiata","Arrabbiata sos je pikantan sos za testeninu, a napravljen je od belog i crnog luka, paradajza i crvene čili papričice, kuvanih u maslinovom ulju. Na italijanskom jeziku “arrabbiata” bukvalno znači “ljut” i odnosi se na ljutinu čili papričica koje se koriste za pripremu.",0F,0,0F,50,Type.MAIN)
        var d11 = Dish("Nutella Pancakes","French Crapes with Nutella",0F,0,0F,35,Type.DESERT)
        var d12 = Dish("Baklava","A traditional turkish desert",0F,0,0F,40,Type.DESERT)
        var d13 = Dish("Apple Pie","Typical American pie",0F,0,0F,48,Type.DESERT)

        databaseRef!!.child("Dishes").child(d1.name).setValue(d1)
        databaseRef!!.child("Dishes").child(d2.name).setValue(d2)
        databaseRef!!.child("Dishes").child(d3.name).setValue(d3)
        databaseRef!!.child("Dishes").child(d4.name).setValue(d4)
        databaseRef!!.child("Dishes").child(d5.name).setValue(d5)
        databaseRef!!.child("Dishes").child(d6.name).setValue(d6)
        databaseRef!!.child("Dishes").child(d7.name).setValue(d7)
        databaseRef!!.child("Dishes").child(d8.name).setValue(d8)
        databaseRef!!.child("Dishes").child(d9.name).setValue(d9)
        databaseRef!!.child("Dishes").child(d10.name).setValue(d10)
        databaseRef!!.child("Dishes").child(d11.name).setValue(d11)
        databaseRef!!.child("Dishes").child(d12.name).setValue(d12)
        databaseRef!!.child("Dishes").child(d13.name).setValue(d13)
        */
//        var dref:DatabaseReference=databaseRef!!.child("Receipts")
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//                if(dataSnapshot.exists()){
//                    Log.d(tag, "loadPost:onComplete")
//                    generateCode(dataSnapshot)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        }
//        dref.addValueEventListener(postListener)



        //databaseRef!!.child("Database").child(userId).child("username").setValue(name)
    }

    fun getDishes(dataSnapshot:DataSnapshot){
        arrayOfDishes=ArrayList<Dish>()
        var m=0
        for(i in dataSnapshot.children){
            var dish = i.getValue(Dish::class.java)
            arrayOfDishes!!.add(dish!!)

        }

/*
        for(i in 1..150){
            var date:String = "2019"+Random.nextInt(10,12).toString()+ (10+i%18).toString()
            var numD= arrayOfDishes!!.size



            for(j in 1..5){
                var rate =Random.nextInt(3,6)
                var k = databaseRef!!.child("Reviews").child(arrayOfDishes!![i%numD].name).child(date!!).push().key
                var r = Review(rate.toFloat() ,date,"")
                var sum=0f
                var num=0

                sum=arrayOfDishes!![i%numD].sumGrade
                num=arrayOfDishes!![i%numD].numOfRev

                sum+=rate
                num++


                val avg = sum/(num.toFloat())
                arrayOfDishes!![i%numD].numOfRev=num
                arrayOfDishes!![i%numD].grade=avg.toFloat()
                arrayOfDishes!![i%numD].sumGrade=sum
                databaseRef!!.child("Reviews").child(arrayOfDishes!![i%numD].name).child(date!!).child(k!!).child("rating").setValue(r)

            }



            //databaseRef!!.child("Reservations").child(date).setValue(tableList)

        }
        var s= arrayOfDishes!!.size
        for(i in 0..s-1){
            databaseRef!!.child("Dishes").child(arrayOfDishes!![i].name).child("sumGrade").setValue(arrayOfDishes!![i].sumGrade)
            databaseRef!!.child("Dishes").child(arrayOfDishes!![i].name).child("numOfRev").setValue(arrayOfDishes!![i].numOfRev)
            databaseRef!!.child("Dishes").child(arrayOfDishes!![i].name).child("grade").setValue(arrayOfDishes!![i].grade)
        }
        */



    }

    fun generateCode(){
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)

        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        var qrgEncoder = QRGEncoder(
            receiptList[itr++], null,
            QRGContents.Type.TEXT,
            smallerDimension
        )
        try {
            bitmap = qrgEncoder.bitmap
            qrImage!!.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.d(tag, "GRESKA")
        }
    }
    fun generateReceipts( arr:ArrayList<Dish>){

        //var count= arr.size
        for (itr in 1..100){
            var r = Receipt(0f)
            var i= Random.nextInt(0,12)
            var j=Random.nextInt(0,12)
            while(j==i)
                j=Random.nextInt(0,12)
            var ic= Random.nextInt(1,3)
            var jc= Random.nextInt(1,3)

            var isum= ic * arr[i].price
            var jsum= jc * arr[j].price

            var map = HashMap<String,Int>()

            map.set(arr[i].name,ic)
            map.set(arr[j].name,jc)

            var k= databaseRef!!.child("Receipts").push().key
            r.bill=(isum+jsum)
            r.orders=map
            databaseRef!!.child("Receipts").child(k!!).setValue(r)

        }


    }


}
