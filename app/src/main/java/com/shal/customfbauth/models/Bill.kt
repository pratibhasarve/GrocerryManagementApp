package com.shal.customfbauth.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Bill(@PrimaryKey val id: Long = 0,
                @ColumnInfo(name = "grocery_name") val name: String? = null,
                @ColumnInfo(name = "quantity") val groceryQuantity: Int? = null,
                @ColumnInfo(name = "date_created") val billDateCreated: Date? = null,
                @ColumnInfo(name = "bill_id") val billId: Long? = null): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("billDateCreated"),
        parcel.readValue(Long::class.java.classLoader) as? Long) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeValue(groceryQuantity)
        parcel.writeValue(billId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bill> {
        override fun createFromParcel(parcel: Parcel): Bill {
            return Bill(parcel)
        }

        override fun newArray(size: Int): Array<Bill?> {
            return arrayOfNulls(size)
        }
    }


}