package com.shal.customfbauth.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shal.customfbauth.models.Bill
import com.shal.customfbauth.models.Grocery
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Insert
    suspend fun insert(bills: List<Bill>)

    @Insert
    suspend fun insert(bill:Bill)

    @Query("SELECT * FROM Bill WHERE bill_id LIKE :billId")
    fun getGroceryByBillId(billId: Long): Flow<List<Bill>>
}