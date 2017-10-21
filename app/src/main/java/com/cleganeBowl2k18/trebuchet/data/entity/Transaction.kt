package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-10-20.
 */
class Transaction {

    @SerializedName("id")
    var id: Long = 0
    var group: Group? = null
    var label: String? = null
    var amount: Double = 0.0
    var resolved: Boolean = false
    var paySplit: MutableMap<User, Double> = mutableMapOf<User,Double>()
    var oweSplit: MutableMap<User, Double> = mutableMapOf<User,Double>()
    // TODO: add date

    constructor()

    constructor(id: Long, group: Group?, label: String?, amount: Double, paySplit: MutableMap<User, Double>, oweSplit: MutableMap<User, Double>) {
        this.id = id
        this.group = group
        this.label = label
        this.amount = amount
        this.paySplit = paySplit
        this.oweSplit = oweSplit
        // TODO: add date
    }
}