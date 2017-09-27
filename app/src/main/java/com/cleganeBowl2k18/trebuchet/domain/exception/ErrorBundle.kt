package com.cleganeBowl2k18.trebuchet.domain.exception

interface ErrorBundle {

    val exception: Exception?

    val errorMessage: String
}