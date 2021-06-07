package com.shal.customfbauth.database

import android.app.Application
import android.util.Log
import com.shal.customfbauth.models.Grocery
import com.shal.customfbauth.utils.FirebaseUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GroceryRepository(application: Application) {

    private val groceryDao: GroceryDao
    var groceries: Flow<List<Grocery>>
    private val coroutineScope = CoroutineScope(IO)

    init {
        val db = GroceryManagementDb.getDatabase(application)
        groceryDao = db!!.groceryDao()
        groceries = groceryDao.getGroceriesNotInCart()
    }

    fun getGroceriesFromCart(): Flow<List<Grocery>> {
        return groceryDao.getGroceriesInCart()
    }

    fun updateCart(grocery: Grocery){
        coroutineScope.launch {
            groceryDao.updateCart(grocery)
        }
    }

    fun deleteAllGroceries() {
        coroutineScope.launch {
            val flag = groceryDao.dropGroceryTable()
            Log.i("TAG", "DELETE DATA $flag")
        }
    }

  /*  fun deleteGroceryById(grocery: Grocery){
        coroutineScope.launch {
            val flag = groceryDao.deleteGroceriesById(grocery)
            Log.i("TAG", "DELETE DATA $flag")
        }
    }*/

   fun deleteGroceryById(id: Long){
        coroutineScope.launch {
            val flag = groceryDao.deleteGroceriesById(id)
            Log.i("TAG", "DELETE DATA $flag")
        }
    }

    fun insert(groceryList: List<Grocery>) {
        coroutineScope.launch {
            groceryDao.insert(groceryList)
        }
    }

    fun insert(grocery: Grocery) {
        coroutineScope.launch {
            groceryDao.insert(grocery)
        }
    }

    fun getGroceryFromFirebase() {
        coroutineScope.launch {
            getDataFromFirebase()
        }
    }

    fun deleteFromFirebase(id : Long){
        coroutineScope.launch {
            deleteGroceryFromFirebase(id)
        }
    }

    private fun deleteGroceryFromFirebase(id : Long) {
        FirebaseUtils.firestore.collection("grocery")
            .document(id).delete().addOnSuccessListener {
                Log.i("TAG", "Grocery has been deleted")
            }
            .addOnFailureListener{exception ->
                Log.d("TAG", "get failed with ", exception)}

    }

    private fun getDataFromFirebase() {
        var groceryList = ArrayList<Grocery>()
        FirebaseUtils.firestore.collection("grocery")
            .get()
            .addOnSuccessListener { snapshot ->

                snapshot.documents.forEach {
                    println(it.data) // data print successfully
                    val grocery = it.toObject(Grocery::class.java)
                    if (grocery != null) {
                        Log.i("TAG", "Fetching data from firebase ${grocery.name}")
                        groceryList.add(grocery)
                    }
                }
                coroutineScope.launch {
                    insert(groceryList)
                }

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }


}
