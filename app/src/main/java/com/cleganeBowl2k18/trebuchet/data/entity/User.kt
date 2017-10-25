package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * User Model
 */
class User {

    @SerializedName("id")
    var externalId: Long = 0
    @SerializedName("last_login")
    var lastLogin: String? = null
    //
    var email: String? = null
    @SerializedName("first_name")
    var fName: String? = null
    @SerializedName("last_name")
    var lName: String? = null


    constructor()

    constructor(id: Long, lastLogin: String?, email: String?, fName: String?, lName: String?) {
        this.externalId = id
        this.lastLogin = lastLogin
        this.email = email
        this.fName = fName
        this.lName = lName
    }

    override fun toString(): String {
        return "User(externalId=$externalId, lastLogin=$lastLogin, email=$email, fName=$fName, lName=$lName)"
    }

}