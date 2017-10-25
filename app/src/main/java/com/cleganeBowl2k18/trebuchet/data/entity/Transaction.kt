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
    var paySplit: MutableMap<User, Double> = mutableMapOf<User,Double>()
    var oweSplit: MutableMap<User, Double> = mutableMapOf<User,Double>()
    var resolved: Boolean = false
    // TODO: add date

    constructor()

    constructor(id: Long, group: Group?, label: String?, amount: Double, paySplit: MutableMap<User, Double>, oweSplit: MutableMap<User, Double>, resolved: Boolean = false) {
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