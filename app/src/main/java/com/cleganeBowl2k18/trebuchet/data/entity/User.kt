package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * User Model
 */
class User {

    @SerializedName("id")
    var email: String? = null
    var lastLogin: String? = null
    var fName: String? = null
    var lName: String? = null
    var externalId: Long = 0

    constructor()

    constructor(id: Long, lastLogin: String?, email: String?, fName: String?, lName: String? ) {
        this.externalId = id
        this.lastLogin = lastLogin
        this.email = email
        this.fName = fName
        this.lName = lName

    }

}