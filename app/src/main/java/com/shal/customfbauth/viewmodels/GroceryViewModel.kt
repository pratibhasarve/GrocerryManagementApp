package com.shal.customfbauth.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.shal.customfbauth.database.GroceryRepository
import com.shal.customfbauth.models.Grocery

class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val groceryRepository = GroceryRepository(application)
    val groceries: LiveData<List<Grocery>> = groceryRepository.groceries.asLiveData()

    fun insert(grocery: Grocery) {
        groceryRepository.insert(grocery)
    }

    fun getGroceryFromFirebase() {
        groceryRepository.getGroceryFromFirebase()
    }

    fun updateCart(grocery: Grocery){
        groceryRepository.updateCart(grocery)
    }

    fun getGroceriesFromCart(): LiveData<List<Grocery>>{
       return groceryRepository.getGroceriesFromCart().asLiveData()
    }

    fun deleteAllGroceries() {
        groceryRepository.deleteAllGroceries()
    }

   /* fun deleteGroceryById(grocery: Grocery){
        groceryRepository.deleteGroceryById(grocery)
    }*/

    fun deleteGroceryById(id: Long){
        groceryRepository.deleteGroceryById(id)
    }

    fun deleteFromFirebase(id: Long){
        groceryRepository.deleteFromFirebase(id)
    }

}