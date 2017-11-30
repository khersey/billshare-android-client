package com.cleganeBowl2k18.trebuchet.data.models

import com.google.gson.annotations.SerializedName

/**
 * User Model
 */
class User {

    @SerializedName("id")
    var externalId: Long = 0
    @SerializedName("last_login")
    var lastLogin: String = " " // initialize with space in case first letter is called for something
    var email: String = " "
    @SerializedName("first_name")
    var fName: String = " "
    @SerializedName("last_name")
    var lName: String = " "


    constructor()

    constructor(id: Long, lastLogin: String, email: String, fName: String, lName: String) {
        this.externalId = id
        this.lastLogin = lastLogin
        this.email = email
        this.fName = fName
        this.lName = lName
    }

    override fun toString(): String {
        return "User(externalId=$externalId, lastLogin=$lastLogin, email=$email, fName=$fName, lName=$lName)"
    }

    fun getUserTitle(): String {
        return "${this.fName} ${this.lName[0]}."
    }
}