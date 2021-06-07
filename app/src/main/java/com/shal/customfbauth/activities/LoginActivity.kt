package com.shal.customfbauth.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.shal.customfbauth.utils.FirebaseUtils
import com.shal.customfbauth.utils.FirebaseUtils.auth
import com.shal.customfbauth.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.prefs.Preferences

class LoginActivity : AppCompatActivity() {

    //Firebase
    private var mAuthListener: AuthStateListener? = null
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)?: return

        val alreadyLogin = sharedPreferences.getBoolean(getString(R.string.already_login), false)
        if(alreadyLogin){
            login()
        }else{
            setupFirebaseAuth()
        }
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnSignIn.setOnClickListener {
            signIn()

        }
        btnForgotPswd.setOnClickListener {
            // forgot password flow
        }
    }

    private fun signIn() {
        var email = editTextEmailAddress.text.toString()
        var password = editTextPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(FirebaseUtils.TAG, "signInWithEmail:success")
                    setupFirebaseAuth()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(FirebaseUtils.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun login() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun setupFirebaseAuth() {
        Log.d(FirebaseUtils.TAG, "setupFirebaseAuth: started.")
        // mAuthListener = AuthStateListener {
        val user = auth.currentUser
        if (user != null) {
            //check if email is verified
            if (user.isEmailVerified) {
                Log.d(FirebaseUtils.TAG, "onAuthStateChanged:signed_in:" + user.uid)
                val user = auth.currentUser
                sharedPreferences.edit().putBoolean(getString(R.string.already_login), true).apply()
                login()
            }
        } else {
            Log.d(FirebaseUtils.TAG, "onAuthStateChanged:signed_out")
        }
        // }
    }

/* override fun onStart() {
     super.onStart()
     FirebaseAuth.getInstance().addAuthStateListener(mAuthListener)
 }

 override fun onStop() {
     super.onStop()
     if (mAuthListener != null) {
         FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener)
     }
 }*/
}