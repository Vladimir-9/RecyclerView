package com.example.lists_2.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.lists_2.CorrectData
import com.example.lists_2.R
import com.example.lists_2.withArguments
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable

class CarDialogFragment : DialogFragment() {

    private lateinit var editTextAutomaker: EditText
    private lateinit var editTextModel: EditText
    private lateinit var editTextCapacity: EditText
    private lateinit var editTextCarLink: EditText
    private lateinit var materialShapeDrawable: MaterialShapeDrawable
    private var wantToCloseDialog = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        materialShapeDrawable = MaterialShapeDrawable()
        materialShapeDrawable.setCornerSize(44F)

        val customDialog: View =
            View.inflate(requireContext(), R.layout.custom_view_dialog, null)

        editTextAutomaker = customDialog.findViewById(R.id.et_automaker)
        editTextModel = customDialog.findViewById(R.id.et_model)
        editTextCapacity = customDialog.findViewById(R.id.et_capacity)
        editTextCarLink = customDialog.findViewById(R.id.et_carLink)

        val dialog = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.MyTheme_MaterialComponents_MaterialAlertDialog
        )
        dialog.apply {
            setView(customDialog)
            background = materialShapeDrawable
            setPositiveButton("Создать") { _, _ -> }
        }
        return dialog.create()
    }

    override fun onResume() {
        super.onResume()
        val changingTheDialogBehavior: AlertDialog? = dialog as AlertDialog?
        if (changingTheDialogBehavior != null) {
            val positiveButton =
                changingTheDialogBehavior.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                wantToCloseDialog =
                    when (parentFragment) {
                        is ListCarFragment -> (parentFragment as? ListCarFragment)
                        is GridListCarFragment -> (parentFragment as? GridListCarFragment)
                        is StaggeredListCarFragment -> (parentFragment as? StaggeredListCarFragment)
                        else -> null
                    }!!.addNewCar(
                        editTextAutomaker.text.toString(),
                        editTextModel.text.toString(),
                        editTextCapacity.text.toString(),
                        editTextCarLink.text.toString(),
                        requireArguments().getBoolean(IS_CARGO_VEHICLE),
                        CorrectData()
                    )
                if (wantToCloseDialog) changingTheDialogBehavior.dismiss()
            }
        }
    }

    companion object {

        private const val IS_CARGO_VEHICLE = "isCargoVehicle"

        fun saveArgCarDialogFragment(isCargoVehicle: Boolean): CarDialogFragment {
            return CarDialogFragment().withArguments {
                putBoolean(IS_CARGO_VEHICLE, isCargoVehicle)
            }
        }
    }
}