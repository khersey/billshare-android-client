package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-10-06.
 */
class User {

    @SerializedName("id")
    var email: String? = null
    var fName: String? = null
    var lName: String? = null
    var externalId: Long = 0

    constructor()

    constructor(externalId: Long, email: String?, fName: String?, lName: String? ) {
        this.externalId = externalId
        this.email = email
        this.fName = fName
        this.lName = lName

    }

}