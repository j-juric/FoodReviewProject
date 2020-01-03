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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class ScanFragment: Fragment() , QRCodeReaderView.OnQRCodeReadListener {


    var myView: View? = null
    var qrCodeReaderView: QRCodeReaderView? = null
    var resultTextView: TextView? = null
    var flag:Boolean=true
    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{

        verifyPremission()

        myView = inflater.inflate(R.layout.fragment_scan,container,false)

        qrCodeReaderView=myView!!.findViewById(R.id.qr_decoder_view) as QRCodeReaderView
        resultTextView=myView!!.findViewById(R.id.result_text)

        Log.d("TAGG",qrCodeReaderView.toString())

        qrCodeReaderView!!.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView!!.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView!!.setAutofocusInterval(2000);


        // Use this function to set back camera preview
        qrCodeReaderView!!.setBackCamera();

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
        qrCodeReaderView!!.startCamera()
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {

        if(flag){
            val intent= Intent(activity!!, MainActivity::class.java)
            startActivity(intent)
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