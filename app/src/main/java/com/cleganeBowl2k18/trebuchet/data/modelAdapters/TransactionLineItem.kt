package com.cleganeBowl2k18.trebuchet.data.modelAdapters

import com.google.gson.annotations.SerializedName

class TransactionLineItem{
    //{"id": 1, "label": "", "bill": 1, "group": 1, "debt":{ "amount": "100.00", "currency": "CAD"},"payee": 2, "payer": 1, "resolved": false}
    var id: Long = 0
    var label: String = ""
    var bill: Long = 0
    var group: Long = 0
    var debt: Map<String, String>? = null
    var payee: Long = 0
    var payer: Long = 0
    var resolved: Boolean = false

    constructor(id: Long, label: String, bill: Long, group: Long, debt: Map<String, String>? = null, payee: Long, payer: Long, resolved: Boolean)
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

