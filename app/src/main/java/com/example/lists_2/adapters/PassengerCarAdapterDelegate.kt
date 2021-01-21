package com.example.lists_2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.lists_2.Car
import com.example.lists_2.CarViewHolder
import com.example.lists_2.R
import com.example.lists_2.databinding.ItemPassengerCarBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class PassengerCarAdapterDelegate(
    private val context: Context,
    private val paramHeight: Int,
    private val paramWidth: Int,
    private val inItemClick: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<Car.PassengerCar, Car, PassengerCarAdapterDelegate.PassengerCarViewHolder>() {

    override fun isForViewType(item: Car, items: MutableList<Car>, position: Int): Boolean {
        return item is Car.PassengerCar
    }

    override fun onCreateViewHolder(parent: ViewGroup): PassengerCarViewHolder {
        val passengerCarViewHolder = PassengerCarViewHolder(
            ItemPassengerCarBinding.inflate(LayoutInflater.from(context), parent, false),
            inItemClick
        )
        passengerCarViewHolder.changeImageView(paramHeight, paramWidth)
        return passengerCarViewHolder
    }

    override fun onBindViewHolder(
        item: Car.PassengerCar,
        holder: PassengerCarViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class PassengerCarViewHolder(
        private var containerView: ItemPassengerCarBinding,
        onItemClick: (position: Int) -> Unit
    ) : CarViewHolder(containerView, onItemClick) {

        init {
            containerView.root.setOnClickListener {
                onItemClick(absoluteAdapterPosition)
            }
        }

        fun changeImageView(paramHeight: Int, paramWidth: Int) {
            containerView.ivCars.layoutParams.height = paramHeight
            containerView.ivCars.layoutParams.width = paramWidth
        }

        override fun bindMainInfo(
            automaker: String,
            model: String,
            carLink: String
        ) {
            containerView.twCar.text = automaker
            containerView.twCarModel.text = model

            Glide
                .with(itemView)
                .load(carLink)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_signal_cellular_connected_no_internet)
                .into(containerView.ivCars)
        }

        fun bind(car: Car.PassengerCar) {
            bindMainInfo(car.automaker, car.model, car.carLink)
            containerView.twCapacity.text = car.capacity.toString()
        }
    }
}