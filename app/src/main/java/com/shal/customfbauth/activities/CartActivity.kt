package com.shal.customfbauth.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shal.customfbauth.R
import com.shal.customfbauth.adapters.CartListAdapter
import com.shal.customfbauth.adapters.GroceryListAdapter
import com.shal.customfbauth.models.Bill
import com.shal.customfbauth.models.Grocery
import com.shal.customfbauth.utils.FirebaseUtils
import com.shal.customfbauth.viewmodels.BillViewModel
import com.shal.customfbauth.viewmodels.GroceryViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class CartActivity : AppCompatActivity() {

    val groceryViewModel: GroceryViewModel by viewModels()
    val billViewModel: BillViewModel by viewModels()
    var adapter: CartListAdapter? = null
    var arrayList : List<Grocery> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        adapter = CartListAdapter(this)
        setAdapter()
        getGroceries()

        btnCheckout.setOnClickListener {
            if (!txtTotal.text.isEmpty()) {
                saveBillIntoDb()
            } else {
                Toast.makeText(this, "Please enter Total !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBillIntoDb() {

        val billId = UUID.randomUUID().getMostSignificantBits()
      // val grosList =  groceryViewModel.getGroceriesFromCart()
        arrayList.forEach{
            val bill = Bill(UUID.randomUUID().getMostSignificantBits(), it.name, it.quantity, Date(Date().time), billId)
            billViewModel.insert(bill)
            groceryViewModel.deleteGroceryById(it.id)
            groceryViewModel.deleteFromFirebase(it.id)
        }

        finish()

    }

    private fun getGroceries() {
        groceryViewModel.getGroceriesFromCart().observe(this, Observer { groceries ->
            Log.i("TAG", " Coroutine groceries size ${groceries.size}")
            //swipeRefrshLayout.isRefreshing = false
            groceries?.let {
                if (groceries.isEmpty()) {
                    groceryViewModel.getGroceryFromFirebase()
                }
                arrayList= groceries
                adapter!!.setGroceries(groceries)
            }
        })

    }

    private fun saveBill() {
        // add bill to the database

/*        for(oldGrocery in finalGroceryList){
            // delete the added grocery item from database
            FirebaseUtils.firestore.collection("grocery")
                .document(oldGrocery.id!!)
                .delete()
                .addOnSuccessListener { documentReference ->
                    Log.w("Dashboard", "document deleted ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w("Dashboard", "Error deleting document $e")
                }
        }*/
    }


    private fun setAdapter() {
        groceryList.adapter = adapter
        groceryList.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        menu?.findItem(R.id.cart)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseUtils.auth.signOut()

                val sharedPreferences =
                    this.getSharedPreferences(getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean(getString(R.string.already_login), false)
                    .apply()

                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    fun updateCart(updatedGrocery: Grocery) {
        groceryViewModel.updateCart(updatedGrocery)
    }

}