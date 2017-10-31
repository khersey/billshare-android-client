package com.cleganeBowl2k18.trebuchet.data.models

import com.google.gson.annotations.SerializedName

class Pet {

    @SerializedName("id")
    var externalId: Long = 0
    var name: String? = null
    var photoUrls: String? = null
    var tags: List<Tag>? = null
    var status: String? = null

    constructor()

    constructor(externalId: Long, name: String?, status: String?) {
        this.externalId = externalId
        this.name = name
        this.status = status
    }

    override fun toString(): String {
        return "Pet(externalId=$externalId, name=$name, photoUrls=$photoUrls, tags=$tags, status=$status)"
    }
}