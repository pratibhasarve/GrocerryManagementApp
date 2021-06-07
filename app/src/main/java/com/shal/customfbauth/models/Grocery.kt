package com.shal.customfbauth.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

@Entity
data class Grocery(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "grocery_name") val name: String? = null,
    @ColumnInfo(name = "quantity") val quantity: Int? = null,
    @ColumnInfo(name = "cart") var cart: Boolean = false,
    @ColumnInfo(name = "date_created") val dateCreated: Date? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte(),
        TODO("dateCreated")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeValue(quantity)
        parcel.writeByte(if (cart) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Grocery> {
        override fun createFromParcel(parcel: Parcel): Grocery {
            return Grocery(parcel)
        }

        override fun newArray(size: Int): Array<Grocery?> {
            return arrayOfNulls(size)
        }
    }

}
