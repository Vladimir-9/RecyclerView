package com.example.lists_2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Car : Parcelable {
    @Parcelize
    data class PassengerCar(
        val id: Long,
        val automaker: String,
        val model: String,
        val capacity: Int,
        val carLink: String
    ) : Car()

    @Parcelize
    data class CargoVehicle(
        val id: Long,
        val automaker: String,
        val model: String,
        val tonnage: Int,
        val carLink: String
    ) : Car()
}