package com.food.review

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_main.*

import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener,RegisterFragment.OnFragmentInteractionListener/*, View.OnClickListener*/{
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Closing Activity")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("No", null)
            .show()
    }

    //var login:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        this.login.setOnClickListener(this)
//        this.register.setOnClickListener(this)
//        this.testing.setOnClickListener(this)
        Log.d("TAGG", "onCreate.")
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        val fragment = LoginFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAGG", "onStart.")
        //var currentUser = mAuth!!.currentUser
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun openTesting(){
        val intent= Intent(this,DatabseTestingActivity::class.java)
        startActivity(intent)
    }

}
