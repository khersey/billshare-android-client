package com.cleganeBowl2k18.trebuchet.data.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-11-08.
 */
class TransactionResolverLineItem {
    @SerializedName("transaction_line_item")
    var id: Long = 0
    var resolved: Boolean = true

    constructor()

    constructor(id: Long) {
        this.id = id
    }

    constructor(id: Long, resolved: Boolean) {
        this.id = id
        this.resolved = resolved
    }
}