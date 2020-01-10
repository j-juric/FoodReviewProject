package com.food.review

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var database:Database?=null
    var mAuth: FirebaseAuth?=null
    var databaseRef: DatabaseReference?=null

    var customerListener:ValueEventListener?=null
    var ownerListener:ValueEventListener?=null
    var waiterListener:ValueEventListener?=null

    var uid:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        mAuth = FirebaseAuth.getInstance()
        database= Database(mAuth!!)
        databaseRef= FirebaseDatabase.getInstance().reference

        val btn_login = v.findViewById<View>(R.id.btn_login)
        val btn_go_to_register = v.findViewById<View>(R.id.btn_go_to_register)
        val btn_database_testing = v.findViewById<View>(R.id.btn_database_testing)

        var email = v.et_email
        var password = v.et_password


        email!!.setText("jj@jmail.com")
        password!!.setText("test1234")


        customerListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    var customer = dataSnapshot.getValue(Customer::class.java)
                    Log.d("TAGG",customer.toString())
                    val intent= Intent(activity, MainMenu::class.java)
                    intent.putExtra("Uid",customer!!.id)
                    intent.putExtra("firstName",customer.firstName)
                    intent.putExtra("lastName",customer.lastName)
                    intent.putExtra("email",customer.email)
                    intent.putExtra("credits",customer.credits)
                    startActivity(intent)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        ownerListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    var customer = dataSnapshot.getValue(Customer::class.java)
                    Log.d("TAGG",customer.toString())
                    val intent= Intent(activity, MainMenu::class.java)
                    intent.putExtra("Uid",uid)
                    intent.putExtra("Role","Owner")
                    startActivity(intent)

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        waiterListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    var customer = dataSnapshot.getValue(Customer::class.java)
                    Log.d("TAGG",customer.toString())
                    val intent= Intent(activity, MainMenu::class.java)
                    intent.putExtra("Uid",uid)
                    intent.putExtra("Role","Waiter")
                    startActivity(intent)

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }



        //[BUTTON_LISTENERS_START]
        btn_login.setOnClickListener{
            Log.d("TAGG",email.text.toString() + " "+password.text.toString())
            signIn(email.text.toString(),password.text.toString())
        }
        btn_go_to_register.setOnClickListener {
            val fragment = RegisterFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        btn_database_testing.setOnClickListener{
            startActivity(Intent(activity,DatabseTestingActivity::class.java))
        }
        //[BUTTON_LISTENERS_END]

        return v
    }

    //[NEBITAN_DEO_START]
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
    //[NEBITAN_DEO_END]

    private fun validateForm(email:String,password:String): Boolean {
        var valid = true
        Log.d("TAGG", "Validate Form.")
        //val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            this.et_email.error = "Required."
            valid = false
        } else {
            this.et_email.error = null
        }

        //val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            this.et_password.error = "Required."
            valid = false
        } else {
            this.et_password.error = null
        }

        return valid
    }

    private fun signIn(email: String, password: String) {
        Log.d("TAGG", "signIn:$email")
        if (!validateForm(email,password)) {
            return
        }

        //showProgressBar()

        // [START sign_in_with_email]
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGG", "signInWithEmail:success")
                    val user = mAuth!!.currentUser
                    uid=user!!.uid

                    var cref = databaseRef!!.child("Users").child("Customers").child(uid!!)
                    var oref = databaseRef!!.child("Users").child("Owner").child(uid!!)
                    var wref = databaseRef!!.child("Users").child("Waiters").child(uid!!)

                    cref.addValueEventListener(customerListener!!)
                    oref.addValueEventListener(ownerListener!!)
                    wref.addValueEventListener(waiterListener!!)

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, "LOG IN FAILED.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // [START_EXCLUDE]

                //hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }
}
