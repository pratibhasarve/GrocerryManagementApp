package com.shal.customfbauth.database

import android.app.Application
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.shal.customfbauth.models.Bill
import com.shal.customfbauth.models.Grocery

@Database(entities = arrayOf(Grocery::class, Bill::class), version = 1)
@TypeConverters(Converter::class)
abstract class GroceryManagementDb : RoomDatabase() {
    abstract fun groceryDao(): GroceryDao
    abstract fun billDeo(): BillDao

    companion object {
        private var groceryDatabase: GroceryManagementDb? = null

        fun getDatabase(application: Application): GroceryManagementDb? {
            if (groceryDatabase == null) {
                synchronized(GroceryManagementDb::class){
                if (groceryDatabase == null) {
                    groceryDatabase = Room.databaseBuilder(
                        application,
                        GroceryManagementDb::class.java, "GroceryManagementDb"
                    ).build()
                }
                }
            }
            return groceryDatabase
        }
    }

}