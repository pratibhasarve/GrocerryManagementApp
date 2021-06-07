package com.shal.customfbauth.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.shal.customfbauth.models.Bill
import com.shal.customfbauth.models.Grocery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BillRepository(application: Application) {

    private val billDao: BillDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val db = GroceryManagementDb.getDatabase(application)
        billDao = db!!.billDeo()
    }

    fun getGroceriesByBillId(billId: Long): Flow<List<Bill>> {
        return billDao.getGroceryByBillId(billId)
    }

    fun insert(billList: List<Bill>) {
        coroutineScope.launch {
            billDao.insert(billList)
        }
    }

    fun insert(bill: Bill) {
        coroutineScope.launch {
            billDao.insert(bill)
        }
    }
}