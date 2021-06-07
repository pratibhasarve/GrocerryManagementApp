package com.shal.customfbauth.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shal.customfbauth.models.Grocery
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {

    @Insert
    suspend fun insert(grocery: Grocery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(groceries: List<Grocery>)

    /* @Query("SELECT * FROM grocery")
     fun getAll(): LiveData<List<Grocery>>
 */
    @Query("SELECT * FROM Grocery")
    fun getAll(): Flow<List<Grocery>>

    @Query("SELECT * FROM Grocery WHERE cart = 0")
    fun getGroceriesNotInCart(): Flow<List<Grocery>>

    @Query("SELECT * FROM Grocery WHERE cart = 1")
    fun getGroceriesInCart(): Flow<List<Grocery>>

    @Update
    fun updateCart(grocery: Grocery)

    @Query("DELETE FROM Grocery")
    suspend fun dropGroceryTable()

    @Query("DELETE FROM Grocery WHERE id  = :id")
    suspend fun deleteGroceriesById(id: Long)

}