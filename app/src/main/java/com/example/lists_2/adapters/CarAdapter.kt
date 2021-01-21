package com.example.lists_2.adapters

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.example.lists_2.Car
import com.example.lists_2.toDp
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CarAdapter(
    context: Context,
    heightCargoCar: Int = 110.toDp(context),
    widthCargoCar: Int = 150.toDp(context),
    heightPassengerCar: Int = 110.toDp(context),
    widthPassengerCar: Int = 150.toDp(context),
    twVisibilityGone: Boolean = false,
    inItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Car>(CarDiffUtilCallBack()) {

    init {
        delegatesManager
            .addDelegate(PassengerCarAdapterDelegate(context, heightPassengerCar, widthPassengerCar, inItemClick))
            .addDelegate(
                CargoCarAdapterDelegate(context, twVisibilityGone, heightCargoCar, widthCargoCar, inItemClick)
            )
    }

    class CarDiffUtilCallBack : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return when {
                oldItem is Car.PassengerCar && newItem is Car.PassengerCar -> oldItem.id == newItem.id
                oldItem is Car.CargoVehicle && newItem is Car.CargoVehicle -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }
}