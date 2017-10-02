package com.cleganeBowl2k18.trebuchet.presentation.common

interface Constants {

    interface Api {
        companion object {
            val BASE_API_URL = "http://petstore.swagger.io/v2/"
        }
    }

    companion object {

        val PET_TAG = "mooveit"
        val PET_STATUS_AVAILABLE = "available"
    }
}