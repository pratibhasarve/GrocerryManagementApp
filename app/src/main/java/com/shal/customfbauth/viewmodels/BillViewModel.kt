package com.shal.customfbauth.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.shal.customfbauth.database.BillRepository
import com.shal.customfbauth.models.Bill

class BillViewModel (application: Application) : AndroidViewModel(application) {
    private val billRepository = BillRepository(application)

    fun insert(bills: List<Bill>){
        billRepository.insert(bills)
    }

    fun insert(bill: Bill){
        billRepository.insert(bill)
    }

    fun getGroceriesByBillId(billId: Long): LiveData<List<Bill>>{
        return billRepository.getGroceriesByBillId(billId).asLiveData()
    }
}