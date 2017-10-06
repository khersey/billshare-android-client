package com.cleganeBowl2k18.trebuchet.data.entity

/**
 * Created by khersey on 2017-10-06.
 */
class User {
    var email: String? = null
    var externalId: Long = 0

    constructor()

    constructor(email: String?, externalId: Long) {
        this.email = email
        this.externalId = externalId
    }

}