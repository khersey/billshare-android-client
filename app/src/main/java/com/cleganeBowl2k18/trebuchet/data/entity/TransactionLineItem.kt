package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

class TransactionLineItem{
    var id: Long = 0
    var label: Long = 0
    var bill: Long = 0
    var group: Long = 0
    @SerializedName("amount")
    var debt: HashMap<String, String>? = null
    var payee: Long = 0
    var payer: Long = 0
    var resolved: Long = 0

    constructor(id: Long, label: Long, bill: Long, group: Long, debt: HashMap<String, String>? = null, payee: Long, payer: Long, resolved: Long)
    {
        this.id = id
        this.label = label
        this.bill = bill
        this.group = group
        this.debt = debt
        this.payee = payee
        this.payer = payer
        this.resolved = resolved
    }
}
