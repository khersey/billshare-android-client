package com.cleganeBowl2k18.trebuchet.domain.exception

class DefaultErrorBundle(override val exception: Exception?) : ErrorBundle {

    override val errorMessage: String
        get() = if (this.exception != null && this.exception.message != null)
            this.exception.message!!
        else
            DEFAULT_ERROR_MSG

    companion object {

        private val DEFAULT_ERROR_MSG = "Unknown error"
    }
}