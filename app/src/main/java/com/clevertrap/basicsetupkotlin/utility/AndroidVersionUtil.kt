/*! * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @File:
 *		STAAndroidVersionUtil.java
 * @Project:
 *		Stardom
 * @Abstract:
 *		
 * @Copyright:
 *     		Copyright © 2012-2013, Viacom 18 Media Pvt. Ltd 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*! Revision history (Most recent first)
 Created by pankaj on 02-Jan-2014
 */

package com.clevertrap.basicsetupkotlin.utility

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View

object AndroidVersionUtil {

    val androidVersion: Int
        get() = Build.VERSION.SDK_INT

    val isBeforeOreo: Boolean
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) true else false

    val isBeforeMarshmallow: Boolean
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true else false

    fun getUniqueDeviceIdentification(context: Context): String {


        /*
		 * Combination of deviceId,AndroidId and SerialNumber
		 */

        val telephonyManager = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val sb = StringBuilder()

        sb.append(if (TextUtils.isEmpty(telephonyManager.deviceId))
            ""
        else
            telephonyManager.deviceId)

        sb.append(if (TextUtils.isEmpty(Settings.Secure.getString(
                        context.contentResolver, Settings.Secure.ANDROID_ID)))
            ""
        else
            Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID))

        sb.append(if (TextUtils.isEmpty(Build.SERIAL))
            ""
        else
            Build.SERIAL)

        return sb.toString()

    }

    fun getVersionCode(context: Context): Int {

        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return info!!.versionCode
    }

    fun getVersionName(context: Context): String {

        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return if (info != null) info.versionName else "0.0.0"
    }

    fun converPixelsToSp(px: Float, context: Context): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }

    fun isTouchOutsideInitialPosition(event: MotionEvent, view: View): Boolean {
        return event.x > view.x + view.width
    }
}
