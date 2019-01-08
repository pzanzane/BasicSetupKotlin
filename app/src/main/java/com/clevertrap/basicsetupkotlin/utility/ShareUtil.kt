package com.fidelitservices.brownstone.utility

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

object ShareUtil {

    fun openWhatsApp(context: Context, mobile: String, text: String) {
        val url = "https://api.whatsapp.com/send?phone="+mobile+"&text="+text

        Log.d("WASTE","url :: "+url)

        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)

    }

    fun sendEmail(context: Context, email: String, subject: String,message: String){

        val emailIntent = Intent(android.content.Intent.ACTION_SEND)
        emailIntent.setType("text/plain")
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf<String>(email))
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message)
        emailIntent.setType("message/rfc822")

        try {
            context.startActivity(Intent.createChooser(emailIntent,
                    "Send email using..."))
        }catch (e: ActivityNotFoundException){
            ToastUtils.showToast("No email clients installed.")
        }

    }
}