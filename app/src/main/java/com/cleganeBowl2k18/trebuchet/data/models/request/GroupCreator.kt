package com.cleganeBowl2k18.trebuchet.data.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-10-23.
 */
class GroupCreator {

    var label: String? = null
    var creator: String? = null
    @SerializedName("group_users")
    var users: List<String>? = null

    constructor()

    constructor(label: String?, creator: String?, users: List<String>?) {
        this.label = label
        this.creator = creator
        this.users = users
    }
}