package com.cleganeBowl2k18.trebuchet.presentation.common

interface Constants {

    interface Api {
        companion object {
            val BASE_API_URL = "https://api.billshare.io/"
        }
    }

    companion object {
        // SharedPreference files
        val PREFS_FILENAME  = "com.cleganeBowl2k18.trebuchet.prefs"

        // SharedPreference IDs
        val LOGGED_IN       = "logged_in"
        val CURRENT_USER_ID = "current_user_id"

        // Split Types
        val SPLIT_EQUALLY       = 0
        val SPLIT_BY_AMOUNT     = 1
        val SPLIT_BY_PERCENTAGE = 2
    }
}
