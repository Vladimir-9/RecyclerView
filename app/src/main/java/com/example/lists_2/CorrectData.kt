package com.example.lists_2

import android.content.Context
import android.widget.Toast

class CorrectData {
    fun isCorrectData(
        automaker: String,
        model: String,
        capacity: String,
        carLink: String,
        context: Context
    ): Boolean {
        if (automaker.matches("".toRegex())) {
            makeToast("Введите автопроизводителя", context)
            return false
        }

        if (model.matches("".toRegex())) {
            makeToast("Введите модель", context)
            return false
        }

        if (capacity.matches("".toRegex())) {
            makeToast("Введите вместимость", context)
            return false
        }

        if (carLink.matches("".toRegex())) {
            makeToast("Введите ссылку", context)
            return false
        }
        return true
    }

    private fun makeToast(massage: String, context: Context) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}