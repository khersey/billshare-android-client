package com.cleganeBowl2k18.trebuchet.data.exception

import com.cleganeBowl2k18.trebuchet.domain.exception.ErrorBundle

internal class RepositoryErrorBundle(override val exception: Exception?) : ErrorBundle {

    override val errorMessage: String
        get() {
            var message = ""
            if (this.exception != null && this.exception.message != null) {
                message = this.exception.message!!
            }
            return message
        }
}