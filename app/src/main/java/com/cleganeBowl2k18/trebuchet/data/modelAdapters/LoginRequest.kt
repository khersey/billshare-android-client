package com.cleganeBowl2k18.trebuchet.data.modelAdapters

/**
 * Created by khersey on 2017-11-21.
 */
class LoginRequest {
    var email: String = ""
    var password: String =""

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}