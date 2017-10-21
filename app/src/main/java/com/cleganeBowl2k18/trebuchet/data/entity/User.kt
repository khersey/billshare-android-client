package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * User Model
 */
class User {

    @SerializedName("id")
    var email: String? = null
    var fName: String? = null
    var lName: String? = null
    var id: Long = 0

    constructor()

    constructor(id: Long, email: String?, fName: String?, lName: String? ) {
        this.id = id
        this.email = email
        this.fName = fName
        this.lName = lName

    }

}