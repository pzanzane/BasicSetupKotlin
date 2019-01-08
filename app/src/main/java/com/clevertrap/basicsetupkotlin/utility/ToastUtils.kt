package com.fidelitservices.brownstone.utility

import android.widget.Toast
import com.clevertrap.basicsetupkotlin.application.ApplicationBasicSetupKotlin


class ToastUtils {

    companion object {

        private var toast: Toast? = null

        fun showToast(str: String) {

            if (toast == null){
                toast = Toast.makeText(
                        ApplicationBasicSetupKotlin.applicationContext(), str,
                        Toast.LENGTH_SHORT)
            }

            toast!!.setText(str)
            toast!!.show()
        }

    }
}