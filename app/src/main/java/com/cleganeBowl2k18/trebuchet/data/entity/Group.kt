package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-10-15.
 */
class Group {

    @SerializedName("id")
    var externalId: Long = 0
    var name: String? = null
    var users: List<User>? = null
    var status: String? = null

    constructor()

    constructor(externalId: Long, name: String?, status: String?, users: List<User>?) {
        this.externalId = externalId
        this.name = name
        this.status = status
        this.users = users
    }

    override fun toString(): String {
        return "hello world"
    }
}