package com.clevertrap.basicsetupkotlin.utility

import android.content.Context
import com.clevertrap.basicsetupkotlin.application.ApplicationBasicSetupKotlin

class PreferenceUtils private constructor() {
    private var ctx: Context? = null

    init {
        ctx = ApplicationBasicSetupKotlin.applicationContext()
    }

    fun putString(key: String, value: String) {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.putString(key, value)
        edt.commit()
    }

    fun getString(key: String, defaultValue: String): String? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getString(key, defaultValue)
    }

    fun getString(key: String): String? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getString(key, "")
    }


    fun putInteger(key: String, value: Int?) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        if (value != null) {
            edt.putInt(key, value)
        } else {
            edt.remove(key)
        }
        edt.commit()

    }

    fun getInteger(key: String, defaultValue: Int): Int? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getInt(key, defaultValue)
    }

    fun getInteger(key: String): Int? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getInt(key, 0)
    }

    fun putLong(key: String, value: Long?) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.putLong(key, value!!)
        edt.commit()

    }

    fun getLong(key: String): Long? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getLong(key, 0)
    }

    fun putFloat(key: String, value: Float?) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.putFloat(key, value!!)
        edt.commit()

    }

    fun putDouble(key: String, value: Double) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        edt.commit()

    }

    fun getDouble(key: String): Double? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return java.lang.Double.longBitsToDouble(shared.getLong(key, 0))
    }

    fun getFloat(key: String): Float? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getFloat(key, 0f)
    }

    fun putBoolean(key: String, value: Boolean) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.putBoolean(key, value)
        edt.commit()

    }

    fun getBoolean(key: String): Boolean? {

        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        return shared.getBoolean(key, false)
    }

    fun remove(key: String) {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        shared.edit().remove(key)
    }

    fun clearAllExceptFcmToken() {
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        //String fcmToken = shared.getString(Constants.PreferenceConstants.FIREBASE_TOKEN,"");
        //shared.edit().clear().apply();
        //putString(Constants.PreferenceConstants.FIREBASE_TOKEN,fcmToken);
    }

    fun clearSharedPref(){
        val shared = ctx!!.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val edt = shared.edit()
        edt.clear()
        edt.clear()
    }

    companion object {

        private var INSTANCE: PreferenceUtils? = null

        fun getSingleton(): PreferenceUtils {
            if (INSTANCE == null) {
                INSTANCE = PreferenceUtils()
            }
            return INSTANCE!!
        }

        private val PREFERENCE_NAME = "BrownStone"
    }
}
