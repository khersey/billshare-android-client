package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Group Model
 */
class Group {

    @SerializedName("id")
    var id: Long = 0
    var name: String? = null
    var users: List<User>? = null
    var status: String? = null

    constructor()

    constructor(id: Long, name: String?, status: String?, users: List<User>?) {
        this.id = id
        this.name = name
        this.status = status
        this.users = users
    }

    override fun toString(): String {
        return "hello world"
    }

    fun getUsersAsStr(): String {
        var content: String = "This Group is Empty"

        if (users != null && users?.size != 0) {
            content = "Members: "
            users?.forEach{ user -> content += "${user.fName}, "}
            return content.substringBeforeLast(',')
        } else {
            return content
        }
    }
}