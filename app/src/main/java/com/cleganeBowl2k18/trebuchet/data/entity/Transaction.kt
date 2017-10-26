package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Transaction Model
 */
class Transaction {

    @SerializedName("id")
    var id: Long = 0
    var group: Group? = null
    var label: String? = null
    var amount: Double = 0.0
    var resolved: Boolean = false
    var paySplit: MutableMap<Long, Double> = mutableMapOf<Long,Double>()
    var oweSplit: MutableMap<Long, Double> = mutableMapOf<Long,Double>()
    // TODO: add date

    constructor()

    constructor(id: Long, group: Group?, label: String?, amount: Double, paySplit: MutableMap<Long, Double>, oweSplit: MutableMap<Long, Double>) {
        this.id = id
        this.group = group
        this.label = label
        this.amount = amount
        this.paySplit = paySplit
        this.oweSplit = oweSplit
        this.resolved = resolved
        // TODO: add date
    }
}