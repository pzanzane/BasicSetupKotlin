package com.clevertrap.basicsetupkotlin.utility

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.util.Log

object CallUtil {

    fun call(context: Context, phone: String): Boolean {

        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("WASTE", "Permission to record denied")
            return false
        }
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phone")
        context.startActivity(intent)

        return true
    }
}
