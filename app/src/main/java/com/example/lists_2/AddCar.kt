package com.example.lists_2

interface AddCar {
    fun addNewCar(
        automaker: String,
        model: String,
        capacity: String,
        carLink: String,
        isCargoVehicle: Boolean,
        isCorrectDataNewCar: CorrectData
    ) : Boolean
}