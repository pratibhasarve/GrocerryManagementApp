package com.shal.customfbauth.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseUtils{
    val TAG = "Firebase logs"
    val auth: FirebaseAuth = Firebase.auth
    val firestore = Firebase.firestore
}