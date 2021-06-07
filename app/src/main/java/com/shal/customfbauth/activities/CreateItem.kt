package com.shal.customfbauth.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shal.customfbauth.R
import com.shal.customfbauth.activities.DashboardActivity.Companion.ACTIVITY_RESULT_REQUEST
import com.shal.customfbauth.activities.DashboardActivity.Companion.INTENT_GROCERY
import com.shal.customfbauth.database.Converter
import com.shal.customfbauth.models.Grocery
import com.shal.customfbauth.utils.FirebaseUtils
import com.shal.customfbauth.utils.Utils
import com.shal.customfbauth.viewmodels.GroceryViewModel
import kotlinx.android.synthetic.main.activity_create_item.*
import java.util.*
import kotlin.random.Random

class CreateItem : AppCompatActivity() {

    val model: GroceryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)
        btnAddItem.setOnClickListener { addItem() }
    }

    private fun addItem() {
        val itemName = edtItemName.text.toString()
        val itemQuantity = edtQuantity.text.toString()
        if (!Utils.isEmpty(itemName) && !Utils.isEmpty(itemQuantity)) {
            addGrocery(itemName, itemQuantity)
        } else {
            Toast.makeText(this, "Please fill the item details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addGrocery(itemName: String, itemQuantity: String) {
        val id = UUID.randomUUID().getMostSignificantBits()
        val grocery = Grocery(id, itemName, itemQuantity.toInt(), false,Date((Date().time)))

        FirebaseUtils.firestore.collection("grocery")
            .add(grocery)
            .addOnSuccessListener { documentReference ->
                Log.w("Dashboard", "document added ${documentReference.id}")

                edtItemName.text.clear()
                edtQuantity.text.clear()

                model.insert(grocery)
                finish()

            }
            .addOnFailureListener { e ->
                Log.w("Dashboard", "Error adding document $e")
            }
    }
}