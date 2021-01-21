package com.example.lists_2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.example.lists_2.Car
import com.example.lists_2.CarViewHolder
import com.example.lists_2.R
import com.example.lists_2.databinding.ItemCargoCarBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CargoCarAdapterDelegate(
    private val context: Context,
    private val twVisibilityGone: Boolean,
    private val paramHeight: Int,
    private val paramWidth: Int,
    private val inItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Car.CargoVehicle, Car, CargoCarAdapterDelegate.CargoVehicleViewHolder>() {

    override fun isForViewType(item: Car, items: MutableList<Car>, position: Int): Boolean {
        return item is Car.CargoVehicle
    }

    override fun onCreateViewHolder(parent: ViewGroup): CargoVehicleViewHolder {
        val cargoVehicleViewHolder = CargoVehicleViewHolder(
            ItemCargoCarBinding.inflate(LayoutInflater.from(context), parent, false),
            inItemClick
        )
        cargoVehicleViewHolder.changeImageView(paramHeight, paramWidth, twVisibilityGone)
        return cargoVehicleViewHolder
    }

    override fun onBindViewHolder(
        item: Car.CargoVehicle,
        holder: CargoVehicleViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CargoVehicleViewHolder(
        private val containerView: ItemCargoCarBinding,
        inItemClick: (position: Int) -> Unit
    ) : CarViewHolder(containerView, inItemClick) {

        fun changeImageView(paramHeight: Int, paramWidth: Int, twVisibilityGone: Boolean) {
            if (twVisibilityGone) {
                containerView.twCar.visibility = View.GONE
                containerView.twCarModel.visibility = View.GONE
                containerView.twCapacity.visibility = View.GONE

                containerView.ivCars.adjustViewBounds = true

                val positionImageView = ConstraintSet()
                positionImageView.connect(
                    R.id.iv_cars,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                positionImageView.connect(
                    R.id.iv_cars,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                positionImageView.connect(
                    R.id.iv_cars,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP
                )
                positionImageView.applyTo(containerView.constraintLayout)
            }
            containerView.ivCars.layoutParams.height = paramHeight
            containerView.ivCars.layoutParams.width = paramWidth
        }

        override fun bindMainInfo(automaker: String, model: String, carLink: String) {
            containerView.twCar.text = automaker
            containerView.twCarModel.text = model

            Glide
                .with(itemView)
                .load(carLink)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_signal_cellular_connected_no_internet)
                .into(containerView.ivCars)

        }

        fun bind(car: Car.CargoVehicle) {
            bindMainInfo(car.automaker, car.model, car.carLink)
            containerView.twCapacity.text = car.tonnage.toString()
        }

    }
}