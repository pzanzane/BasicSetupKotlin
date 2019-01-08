package com.clevertrap.basicsetupkotlin.utility

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log

class DeviceInfoUtil {

    public fun toastDeviceConfig(context:Context,showDialog: Boolean) {


        var displayMetrics: DisplayMetrics = context.resources.displayMetrics
        var dpHeight: Float = displayMetrics.heightPixels / displayMetrics.density
        var dpWidth: Float = displayMetrics.widthPixels / displayMetrics.density

        if(showDialog) {


            var msg: StringBuilder = StringBuilder()
            msg.append("Pixels:           ")
            msg.append(displayMetrics.widthPixels)
            msg.append("\n")
            msg.append(" X ")
            msg.append(displayMetrics.heightPixels)
            msg.append("\n")
            msg.append("dpHeight:         ")
            msg.append(dpHeight)
            msg.append("\n")
            msg.append("dpWidth:         ")
            msg.append(dpWidth)
            msg.append("\n")
            msg.append("density:         ")
            msg.append(displayMetrics.density)
            msg.append("\n")
            msg.append("densityDpi:         ")
            msg.append(displayMetrics.densityDpi)
            msg.append("\n")
            /*msg.append("deviceId:         ")
            msg.append(AndroidVersionUtil.getUniqueDeviceIdentification(this))
            msg.append("\n")*/
//            msg.append("resourceFolder:         ")
//            msg.append(getResources().getString(R.string.screen_size))
//            msg.append("\n")
            Log.d("WASTE",msg.toString())


        }
    }
}