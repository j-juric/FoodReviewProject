package com.food.review

import android.app.Activity
import android.content.Context
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.et_email
import kotlinx.android.synthetic.main.fragment_login.et_password
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.et_email
import kotlinx.android.synthetic.main.fragment_login.view.et_password
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var database:Database?=null
    var mAuth: FirebaseAuth?=null
    var databaseRef:DatabaseReference?=null

    var postListener:ValueEventListener?=null

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
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        database= Database(mAuth!!)
        databaseRef= FirebaseDatabase.getInstance().reference

        val btn_register = v.findViewById<View>(R.id.btn_register)
        val btn_go_to_login = v.findViewById<View>(R.id.btn_go_to_login)


        var e_firstName=v.et_firstName
        var e_lastName=v.et_lastName
        var e_email = v.et_email_reg
        var e_password = v.et_password_reg
        var e_repassword = v.et_repassword

         postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.exists()){
                    Log.d(tag, "loadPost:onComplete")

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(tag, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        btn_go_to_login.setOnClickListener {
            val fragment = LoginFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_fragment_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        btn_register.setOnClickListener(){
            val t =validateForm(e_firstName.text.toString(),e_lastName.text.toString(),e_email.text.toString(),
                e_password.text.toString(),e_repassword.text.toString())
            if(t){
                createAccount(e_firstName.text.toString(),e_lastName.text.toString(),e_email.text.toString()
                    ,e_password.text.toString(),e_repassword.text.toString())
            }
            else{
                Toast.makeText(activity, "REGISTRATION INVALID.",
                    Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun validateForm(firstName:String,lastName:String,email:String,password:String,repassword:String): Boolean {

        Log.d("TAGG", "Validate Form.")
        var valid = true
        if (TextUtils.isEmpty(firstName)) {
            et_firstName.error = "Required."
            valid = false
        } else {
            et_firstName.error = null
        }

        if (TextUtils.isEmpty(lastName)) {
            this.et_lastName.error = "Required."
            valid = false
        } else {
            this.et_lastName.error = null
        }

        if (TextUtils.isEmpty(email)) {
            this.et_email_reg.error = "Required."
            valid = false
        } else {
            this.et_email_reg.error = null
        }

        var flag1=true
        var flag2=true
        if (TextUtils.isEmpty(password)) {
            this.et_password_reg.error = "Required."
            valid = false
            flag1=false
        } else {
            this.et_password_reg.error = null
        }

        if (TextUtils.isEmpty(repassword)) {
            this.et_repassword.error = "Required."
            valid = false
            flag2=false
        } else {
            this.et_repassword.error = null
        }
        if(flag1 && flag2){
            if (!password.equals(repassword)){
                this.et_password_reg.error="Password must match in both cases."
            }

        }

        return valid
    }

    private fun createAccount(firstName:String,lastName:String,email:String,password:String,repassword:String) {
        Log.d("TAGG", "createAccount:$email")
        if (!validateForm(firstName,lastName,email,password,repassword)) {
            return
        }

        //showProgressBar()

        // [START create_user_with_email]
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGG", "createUserWithEmail:success")
                    val user = mAuth!!.currentUser
                    val uid=user!!.uid
                    var u = Customer(firstName,lastName,email,uid,0)
                    databaseRef!!.child("Users").child("Customers").child(uid).setValue(u)
                    Toast.makeText(activity,"Registration successful!",Toast.LENGTH_SHORT).show()
                    val fragment = LoginFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.main_fragment_container, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }



                //uref.addValueEventListener(postListener!!)
                //uref.addListenerForSingleValueEvent()
                //uRef.orderByChild("age").startAt("21").endAt("50")
                // [START_EXCLUDE]
                //hideProgressBar()
                // [END_EXCLUDE]
            }
        // [END create_user_with_email]

    }
}
