package com.food.review

import android.Manifest
import android.content.Intent


import android.content.pm.PackageManager
import android.graphics.PointF
import android.media.MediaRecorder.VideoSource.CAMERA
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import kotlinx.android.synthetic.main.fragment_scan.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*


class ScanFragment(val id:String,val arrayOfDishes: ArrayList<Dish>): Fragment() , QRCodeReaderView.OnQRCodeReadListener {



    var myView: View? = null
    var qrCodeReaderView: QRCodeReaderView? = null
    var resultTextView: TextView? = null
    var flag:Boolean=true

    var databaseRef: DatabaseReference?=null
    var scanListener: ValueEventListener?=null
    var receiptText:String?=null

    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{

        //setContentView(R.layout.activity_main_menu)

        verifyPremission()

        databaseRef= FirebaseDatabase.getInstance().reference

        myView = inflater.inflate(R.layout.fragment_scan,container,false)


        qrCodeReaderView=myView!!.findViewById(R.id.qr_decoder_view) as QRCodeReaderView
        resultTextView=myView!!.findViewById(R.id.result_text)

        Log.d("TAGG",qrCodeReaderView.toString())

        qrCodeReaderView!!.setOnQRCodeReadListener(this)

        // Use this function to enable/disable decoding
        qrCodeReaderView!!.setQRDecodingEnabled(true)

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView!!.setAutofocusInterval(2000)


        // Use this function to set back camera preview
        qrCodeReaderView!!.setBackCamera()

        scanListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    var receipt = dataSnapshot.getValue(Receipt::class.java)
                    if (receipt!!.scanned){
                        Toast.makeText(activity, "It appears the code has already been scanned", Toast.LENGTH_LONG).show()
                    }
                    val fragment = RateFragment(receipt,id,dataSnapshot.key!!, arrayOfDishes)
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                Toast.makeText(activity, "On cancel", Toast.LENGTH_LONG).show()
                // ...
            }
        }

        return myView
    }

     override fun onStart() {
         super.onStart()
         qrCodeReaderView!!.startCamera()
    }

    override fun onResume() {
        super.onResume()
        qrCodeReaderView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView!!.stopCamera()
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {

        if(flag){
            Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
            var sref = databaseRef!!.child("Receipts").child(text!!)
            sref.addValueEventListener(scanListener!!)
            flag=false
        }

    }

    fun verifyPremission(){

        var permissions:String = Manifest.permission.CAMERA

        if(ContextCompat.checkSelfPermission(activity!!.applicationContext,
                permissions)==PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(activity!!,
                arrayOf(permissions),CAMERA)
        }
    }

}