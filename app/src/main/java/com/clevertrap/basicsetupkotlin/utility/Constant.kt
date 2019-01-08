package com.clevertrap.basicsetupkotlin.utility

class Constant {

    class LogConstants {

        companion object {
            const val TAG_WASTE = "WASTE"
        }
    }

    class PreferenceConstants {
        companion object {
            const val API_KEY = "API_KEY"
            const val API_SECRET = "API_SECRET"
            const val UNIQUE_INSTALLATION_ID = "UNIQUE_INSTALLATION_ID"
            const val PREF_USERID = "PREF_USERID"
            const val PREF_NAME = "PREF_NAME"
            const val PREF_EMAIL = "PREF_EMAIL"
            const val PREF_MOBILE = "PREF_MOBILE"
        }
    }

    class ActionConstant {
        companion object {
            const val ACTION_BROADCAST_FRESH = "ACTION_BROADCAST_FRESH"
            const val ACTION_SEARCH_QUERY ="SEARCH_QUERY"
        }
    }
}