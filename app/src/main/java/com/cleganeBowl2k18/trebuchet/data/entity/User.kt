package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-10-06.
 */
class User {

    @SerializedName("id")
    var email: String? = null
    var externalId: Long = 0

    constructor()

    constructor(email: String?, externalId: Long) {
        this.email = email
        this.externalId = externalId
    }

}