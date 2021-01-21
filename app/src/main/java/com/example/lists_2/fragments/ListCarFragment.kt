package com.example.lists_2.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lists_2.*
import com.example.lists_2.adapters.CarAdapter
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlin.random.Random.Default.nextLong

class ListCarFragment : Fragment(R.layout.fragment_list), AddCar {
    private lateinit var textViewEmpty: TextView
    private lateinit var recycleView: RecyclerView
    private lateinit var fab: SpeedDialView
    private var listCar = listOf(
        Car.PassengerCar(
            1,
            "Ford",
            "Focus",
            5,
            "https://avilon-trade.ru/img/catalog/ford/focus/3.jpg"
        ),
        Car.PassengerCar(
            2,
            "Toyota",
            "Corolla",
            5,
            "https://images.ru.prom.st/92166191_w640_h640_toyota-corolla-2013.jpg"
        ),
        Car.PassengerCar(
            3,
            "Mitsubishi",
            "pajero",
            7,
            "https://gulliverauto.ru/images/models_auto/other/addimg_mitsubishi_pajero.jpg"
        ),
        Car.PassengerCar(
            4,
            "Subaru",
            "Impreza",
            5,
            "https://i.pinimg.com/originals/64/69/7d/64697de683127505dd97165ab47eb97d.jpg"
        ),
        Car.CargoVehicle(
            5,
            "Ford",
            "Transit",
            1350,
            "https://akppcomplex.ru/wp-content/uploads/2019/07/20141216_00_tranzit_sochi_zr_12_14-1024x681.jpg"
        ),
        Car.CargoVehicle(
            6,
            "Mercedes",
            "Sprinter",
            1005,
            "https://www.automobilesreview.com/gallery/mercedes-sprinter-brilliant-van/mercedes-sprinter-brilliant-van-01.jpg"
        ),
        Car.PassengerCar(
            7,
            "Subaru",
            "Impreza",
            5,
            "https://i.pinimg.com/originals/64/69/7d/64697de683127505dd97165ab47eb97d.jpg"
        ),
        Car.CargoVehicle(
            8,
            "Ford",
            "Transit",
            1350,
            "https://akppcomplex.ru/wp-content/uploads/2019/07/20141216_00_tranzit_sochi_zr_12_14-1024x681.jpg"
        ),
        Car.CargoVehicle(
            9,
            "Mercedes",
            "Sprinter",
            1005,
            "https://autodmir.ru/logo/1/20565/mercedes-sprinter-20565.jpg"
        )
    )

    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var carAdapter: CarAdapter by AutoClearedValue(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recycle_view)
        fab = view.findViewById(R.id.speedDial)
        textViewEmpty = view.findViewById(R.id.tw_empty)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()

        listCar =
            if (savedInstanceState == null) listCar else savedInstanceState.getParcelableArray(
                SAVE_LIST_CAR
            )?.toList() as List<Car>

        fab.setOnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_passenger_car -> {
                    CarDialogFragment.saveArgCarDialogFragment(false)
                        .show(childFragmentManager, null)
                }
                R.id.fab_cargo_car -> {
                    CarDialogFragment.saveArgCarDialogFragment(true)
                        .show(childFragmentManager, null)
                }
            }
            false
        }

        with(fab) {
            addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_passenger_car, R.drawable.ic_car)
                    .setTheme(R.style.Theme_Fab_Car)
                    .create()
            )
            addActionItem(
                SpeedDialActionItem.Builder(R.id.fab_cargo_car, R.drawable.ic_bus)
                    .setTheme(R.style.Theme_Fab_CargoCar)
                    .create()
            )
        }
        carAdapter.items = listCar
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(SAVE_LIST_CAR, listCar.toTypedArray())
    }

    private fun initList() {
        val myLayoutManager = LinearLayoutManager(requireContext())
        scrollListener = object : EndlessRecyclerViewScrollListener(myLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (listCar.size < 72)
                    listCar += listCar
                carAdapter.items = listCar
            }
        }

        carAdapter = CarAdapter(requireContext()) { position ->
            deleteCar(position)
        }
        with(recycleView) {
            adapter = carAdapter
            layoutManager = myLayoutManager
            itemAnimator = ScaleInAnimator()
            addOnScrollListener(scrollListener)
            setHasFixedSize(true)
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun isEmptyListCar() {
        if (listCar.isEmpty()) {
            textViewEmpty.visibility = View.VISIBLE
        } else {
            textViewEmpty.visibility = View.GONE
        }
    }

    private fun deleteCar(position: Int) {
        listCar = listCar.filterIndexed { index, _ -> index != position }
        carAdapter.items = listCar
        isEmptyListCar()
    }

    override fun addNewCar(
        automaker: String,
        model: String,
        capacity: String,
        carLink: String,
        isCargoVehicle: Boolean,
        isCorrectDataNewCar: CorrectData
    ): Boolean {
        if (isCorrectDataNewCar.isCorrectData(
                automaker,
                model,
                capacity,
                carLink,
                requireContext()
            )
        ) {
            val newCar: Car = if (isCargoVehicle)
                Car.CargoVehicle(nextLong(), automaker, model, capacity.toInt(), carLink)
            else
                Car.PassengerCar(nextLong(), automaker, model, capacity.toInt(), carLink)
            listCar = listOf(newCar) + listCar
            carAdapter.items = listCar
            recycleView.scrollToPosition(0)
            isEmptyListCar()
            return true
        } else {
            return false
        }
    }

    companion object {
        const val SAVE_LIST_CAR = "saveListCar"
    }
}