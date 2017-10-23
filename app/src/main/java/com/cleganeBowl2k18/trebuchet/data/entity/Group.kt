package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Group Model
 */
class Group {

    @SerializedName("id")
    var externalId: Long = 0
    var label: String? = null
    @SerializedName("group_users")
    var users: List<User>? = null

    constructor()

    constructor(externalId: Long, label: String?, users: List<User>?) {
        this.externalId = externalId
        this.label = label
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

    // ASSUME GROUP CREATION FORMAT FOR GROUP
    fun toGroupCreator(): GroupCreator {

        var owner : String? = null
        var addUsers : MutableList<String>? = mutableListOf()

        users?.forEach{ user ->
            if (user.externalId.toInt() == 0) {
                addUsers!!.add(user.email!!)
            } else if (user.externalId.toInt() != 0 && owner.isNullOrBlank()){
                owner = user.email
            } else {
                addUsers!!.add(user.email!!)
            }
        }
        // TODO: validation to ensure only one potential owner comes in
        //       this is garuanteed to only return one owner, but no guarantee it's the right one

        return GroupCreator(this.label, owner, addUsers)
    }
}