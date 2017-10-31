package com.cleganeBowl2k18.trebuchet.data.models

import com.google.gson.annotations.SerializedName

/**
 * Transaction Model
 */
class Transaction {

    @SerializedName("id")
    var id: Long = 0
    // uuid perhaps as well
    var group: Group? = null
    var label: String? = null
    var amount: Long = 0
    var currencyCode: String? = null // this should be an ENUM
    var resolved: Boolean = false
    var paySplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()
    var oweSplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()
    // TODO: add date

    constructor()

    constructor(id: Long, group: Group?, label: String?, amount: Long, currencyCode: String, resolved: Boolean, paySplit: MutableMap<Long, Long>, oweSplit: MutableMap<Long, Long>) {
        this.id = id
        this.group = group
        this.label = label
        this.amount = amount
        this.currencyCode = currencyCode
        this.resolved = resolved
        this.paySplit = paySplit
        this.oweSplit = oweSplit
        this.resolved = resolved
        // TODO: add date
    }
}