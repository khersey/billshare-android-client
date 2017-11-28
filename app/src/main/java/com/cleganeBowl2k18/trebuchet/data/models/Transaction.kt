package com.cleganeBowl2k18.trebuchet.data.models

import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
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
    var creator: Long = -1
    var splitType: Int = Constants.SPLIT_BY_AMOUNT
    var paySplit: MutableMap<Long, Long> = mutableMapOf<Long, Long>()
    var oweSplit: MutableMap<Long, Long> = mutableMapOf<Long, Long>()
    var resolved: MutableMap<Long, Boolean> = mutableMapOf<Long, Boolean>()
    var lineItemMap: MutableMap<Long, Long> = mutableMapOf<Long, Long>() // userId => lineId
    // TODO: add date

    constructor()

    // old factory constructor
    constructor(id: Long, group: Group?, label: String?, amount: Long, currencyCode: String?, creator: Long, paySplit: MutableMap<Long, Long>, oweSplit: MutableMap<Long, Long>) {
        this.id = id
        this.group = group
        this.label = label
        this.amount = amount
        this.currencyCode = currencyCode
        this.creator = creator
        this.paySplit = paySplit
        this.oweSplit = oweSplit
    }

    constructor(id: Long, group: Group?, label: String?, amount: Long, currencyCode: String?, creator: Long, splitType: Int, paySplit: MutableMap<Long, Long>, oweSplit: MutableMap<Long, Long>, resolved: MutableMap<Long, Boolean>, lineItemMap: MutableMap<Long, Long>) {
        this.id = id
        this.group = group
        this.label = label
        this.amount = amount
        this.currencyCode = currencyCode
        this.creator = creator
        this.splitType = splitType
        this.paySplit = paySplit
        this.oweSplit = oweSplit
        this.resolved = resolved
        this.lineItemMap = lineItemMap
    }



}