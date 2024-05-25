package com.example.futurerealty

import android.os.Parcel
import android.os.Parcelable

data class realityData(
    var imageUrl: String? = "",
    var price: String? = "",
    var description: String? = "",
    var location: String? = "",
    var contact: String? = ""
): Parcelable {
    constructor() : this("", "", "", "", "")

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeString(location)
        parcel.writeString(imageUrl)
        parcel.writeString(contact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<realityData> {
        override fun createFromParcel(parcel: Parcel): realityData {
            return realityData(parcel)
        }

        override fun newArray(size: Int): Array<realityData?> {
            return arrayOfNulls(size)
        }
    }
}