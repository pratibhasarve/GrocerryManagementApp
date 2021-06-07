package com.shal.customfbauth.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.shal.customfbauth.utils.FirebaseUtils
import com.shal.customfbauth.utils.FirebaseUtils.auth
import com.shal.customfbauth.R
import com.shal.customfbauth.utils.Utils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnSignUp.setOnClickListener { signUp() }
    }

    private fun signUp() {
        var email = editTextEmailAddress.text.toString()
        var password = editTextPassword.text.toString()
        if(!Utils.isEmpty(email) && !Utils.isEmpty(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FirebaseUtils.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        sendVerificationEmail(user)
                        auth.signOut()
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(FirebaseUtils.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //updateUI(null)
                    }
                }
        }else{
            Toast.makeText(
                this,
                "You must fill out all the fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

     private fun sendVerificationEmail(user: FirebaseUser?) {
         user?.sendEmailVerification()?.addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 Log.d(FirebaseUtils.TAG, "Email sent.")
             }else{
                 Toast.makeText(
                     this, "Couldn't Send Verification Email",
                     Toast.LENGTH_SHORT
                 ).show()
             }
         }
     }
}