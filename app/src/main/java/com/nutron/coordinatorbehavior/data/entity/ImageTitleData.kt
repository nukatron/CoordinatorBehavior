package com.nutron.coordinatorbehavior.data.entity

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.DrawableRes


data class ImageTitleData(@DrawableRes val id: Int, val title: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageTitleData> {
        override fun createFromParcel(parcel: Parcel): ImageTitleData {
            return ImageTitleData(parcel)
        }

        override fun newArray(size: Int): Array<ImageTitleData?> {
            return arrayOfNulls(size)
        }
    }
}