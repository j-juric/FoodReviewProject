package com.food.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DatabseTestingActivity : AppCompatActivity() {

    var database:FirebaseDatabase?=null
    var databaseRef:DatabaseReference?=null
    var mAuth: FirebaseAuth?=null

    var arrayOfDishes: ArrayList<Dish> ?= null

    val tag:String="TAGG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_databse_testing)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        mAuth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference
        val ref=database!!.getReference("Database")

        /*arrayOfDishes= ArrayList()
        var d1 = Dish("Carbonara","Italijanska pasta ciji su glavni sastojci neutralna pavlaka, jaja i slanina",0F,0,0F,Type.MAIN)
        var d2 = Dish("Bolognese","It consists of spaghetti served with a sauce made from tomatoes, minced beef, garlic, wine and herbs;",0F,0,0F,Type.MAIN)
        var d3 = Dish("Pesto","The original pesto alla genovese, the quintessential pesto recipe, is made with Genovese basil, coarse salt, garlic, Ligurian extra virgin olive oil (Taggiasco), European pine nuts (sometimes toasted) and a grated cheese like Parmigiano-Reggiano",0F,0,0F,Type.MAIN)
        var d4 = Dish("Quattro Fromaggi","Pasta Quattro Formaggi, which is Italian for 'Pasta with Four Cheeses'. The most commonly used cheese varieties for this recipe are parmesan, gorgonzola, fontina, and provolone or mozzarella.",0F,0,0F,Type.MAIN)
        var d5 = Dish("Aglio e olio","Kaže se da kada bi postojala biblija o pastama, prva rečenica bi glasila, “U početku, Bog stvori “Aglio e Olio”. Drugim rečima, način na koji se danas pripremaju paste, Aglio e Olio je skoro uvek prvi korak.",0F,0,0F,Type.MAIN)
        var d6 = Dish("Napolitana","Napolitana sos se još naziva i Napoli sos, odnosno Napuljski sos, a to je kolektivni naziv za svaki sos koji se bazira na umaku od paradajza, a potiče iz italijanske kuhinje.",0F,0,0F,Type.MAIN)
        var d7 = Dish("Alfredo Tacchini","Alfredo sos je zapravo jedan od najstarijih i najjednostavnijih načina za pripremu testenine. Italijanska verzija ovog sosa sadrži puter i parmezan, dok je američka sa pavlakom i parmezanom.",0F,0,0F,Type.MAIN)
        var d8 = Dish("Amatriciana","Amatriciana je tradicionalni italijanski pasta sos na bazi slanine, rendanog sira, paradajza i crnog luka. Poreklom je iz grada Amatrice i jedan je od najpoznatijih soseva za testenine rimske, odnosno italijanske kuhinje.",0F,0,0F,Type.MAIN)
        var d9 = Dish("Funghi","Postoji puno recepta za sos sa pečurkama, ali ono što čini ovu pastu posebnom jeste savršen spoj sa pavlakom, belim lukom i parmezanom. Funghi sos za pastu je kremas, primamljv i divnog ukusa pa je sasvim opravdano jedan od najtraženijih u Pasta baru 2×2.",0F,0,0F,Type.MAIN)
        var d10 = Dish("Arrabbiata","Arrabbiata sos je pikantan sos za testeninu, a napravljen je od belog i crnog luka, paradajza i crvene čili papričice, kuvanih u maslinovom ulju. Na italijanskom jeziku “arrabbiata” bukvalno znači “ljut” i odnosi se na ljutinu čili papričica koje se koriste za pripremu.",0F,0,0F,Type.MAIN)
        var d11 = Dish("Nutella Pancakes","French Crapes with Nutella",0F,0,0F,Type.DESERT)
        var d12 = Dish("Baklava","A traditional turkish desert",0F,0,0F,Type.DESERT)
        var d13 = Dish("Apple Pie","Typical American pie",0F,0,0F,Type.DESERT)

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

        var dref:DatabaseReference=databaseRef!!.child("Dishes")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    Log.d(tag, "loadPost:onComplete")
                    getDishes(dataSnapshot)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        dref.addValueEventListener(postListener)
        Log.d(tag, ref.toString())
        Log.d(tag,"INITIALIZING APP FROM PID: " + android.os.Process.myPid())
        Log.d(tag,"DISHES ADDED")
        */

        //databaseRef!!.child("Database").child(userId).child("username").setValue(name)
    }

    fun getDishes(dataSnapshot:DataSnapshot){
        for(i in dataSnapshot.children){
            var dish = i.getValue(Dish::class.java)
            arrayOfDishes!!.add(dish!!)
        }
        for( i in arrayOfDishes!!){
            Log.d(tag,i.toString())
        }
    }


}
