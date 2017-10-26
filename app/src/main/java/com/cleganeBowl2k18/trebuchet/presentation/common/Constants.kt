package com.cleganeBowl2k18.trebuchet.presentation.common

import android.content.SharedPreferences

interface Constants {

    interface Api {
        companion object {
            val BASE_API_URL = "http://billshare.io:3000/"
        }
    }

    companion object {
        // SharedPreference files
        val PREFS_FILENAME  = "com.cleganeBowl2k18.trebuchet.prefs"

        // SharedPreference IDs
        val LOGGED_IN       = "logged_in"
        val CURRENT_USER    = "current_user"

        // Split Types
        val SPLIT_EQUALLY       = 0
        val SPLIT_BY_AMOUNT     = 1
        val SPLIT_BY_PERCENTAGE = 2
    }
}