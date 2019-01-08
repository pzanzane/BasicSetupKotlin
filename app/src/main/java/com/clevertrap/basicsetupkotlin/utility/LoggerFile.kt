package com.clevertrap.basicsetupkotlin.utility


import android.os.Environment
import android.util.Log

import java.io.File
import java.util.Calendar

object LoggerFile {

    private val isLoggingEnabled = true
    var filePath: String? = null

    init {
        filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "MobileDelivery"
    }

    fun d(tag: String, message: String) {


        AppExecutor.getSingleton().diskIO.execute{
            Log.d(tag, message)

            if (isLoggingEnabled){
                print(tag, message, "=========================================================")
            }
        }

    }

    fun e(tag: String, message: String) {
        AppExecutor.getSingleton().diskIO.execute{
            Log.e(tag, message)

            if (isLoggingEnabled){
                print(tag, message, ".........................................................")
            }
        }
    }

    private fun print(tag: String, message: String, logSeperator: String) {

        val builder = StringBuilder()
        builder.append("\u200B")
        builder.append("\n\n\n")
        builder.append(logSeperator)
        builder.append("\n")
        builder.append(tag)
        builder.append("\n")
        builder.append(Calendar.getInstance().time.toString())
        builder.append("\n\n")
        builder.append(message)


        /*   IOUtils.writeToFile(builder.toString(), filePath, Constants.LogConstants.COMMON_LOG_FILE);
        IOUtils.writeToFile(builder.toString(), filePath, tag);
*/
    }
}
