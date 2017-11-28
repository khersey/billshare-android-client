package com.cleganeBowl2k18.trebuchet.data.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-11-21.
 */
class CreateUserRequest {
    var email: String = ""
    var password: String = ""
    @SerializedName("first_name")
    var fName: String = ""
    @SerializedName("last_name")
    var lName: String = ""

    constructor()

    constructor(email: String, password: String, fName: String, lName: String) {
        this.email = email
        this.password = password
        this.fName = fName
        this.lName = lName
    }
}