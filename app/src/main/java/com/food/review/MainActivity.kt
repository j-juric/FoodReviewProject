package com.food.review

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity(), View.OnClickListener{

    var database:Database?=null
    var mAuth:FirebaseAuth?=null
    //var login:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.login.setOnClickListener(this)
        this.register.setOnClickListener(this)
        Log.d("TAGG", "onCreate.")
        /////////////////////////
        mAuth = FirebaseAuth.getInstance()
        database= Database(mAuth!!)
        var email=this.fieldEmail
        email.setText("test2@test.com")
        var password=this.fieldPassword
        password.setText("test1234")


        //////////////////////////
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

    private fun signIn(email: String, password: String) {
        Log.d("TAGG", "signIn:$email")
        if (!validateForm(email,password)) {
            return
        }

        //showProgressBar()

        // [START sign_in_with_email]
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGG", "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    Toast.makeText(baseContext, "LOG IN SUCCESSFUL.",
                        Toast.LENGTH_SHORT).show()

                    val intent= Intent(this, MainMenu::class.java)
                    startActivity(intent)

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "LOG IN FAILED.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // [START_EXCLUDE]

                //hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }

    private fun createAccount(email: String, password: String) {
        Log.d("TAGG", "createAccount:$email")
        if (!validateForm(email,password)) {
            return
        }

        //showProgressBar()

        // [START create_user_with_email]
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGG", "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    Toast.makeText(baseContext, "Bravo ti ga batko.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // [START_EXCLUDE]
                //hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END create_user_with_email]
    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAGG", "Email sent.")
                }
            }
        // [END send_email_verification]
    }

    private fun sendPasswordReset() {
        // [START send_password_reset]
        val auth = FirebaseAuth.getInstance()
        val emailAddress = "user@example.com"

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAGG", "Email sent.")
                }
            }
        // [END send_password_reset]
    }



    private fun validateForm(email:String,password:String): Boolean {
        var valid = true
        Log.d("TAGG", "Validate Form.")
        //val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        //val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View) {
        val i = v.id
        Log.d("TAGG", "Click.")
        when (i) {
            R.id.register -> createAccount(fieldEmailRegister.text.toString(), fieldPasswordRegister.text.toString())
            R.id.login -> signIn(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
    }

}
