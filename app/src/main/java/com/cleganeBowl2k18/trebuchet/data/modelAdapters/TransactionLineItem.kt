package com.cleganeBowl2k18.trebuchet.data.modelAdapters

import com.google.gson.annotations.SerializedName

class TransactionLineItem{

    var id: Long = 0
    var label: String = ""
    @SerializedName("debt_currency")
    var currencyCode: String = ""
    var debt: Double = 0.0
    var resolved: Boolean = false
    var transaction: Long = 0
    var group: Long = 0
    var debtor: Long = 0
    var creditor: Long = 0

    constructor(id: Long, label: String, currencyCode: String, debt: Double, resolved: Boolean, transaction: Long, group: Long, debtor: Long, creditor: Long) {
        this.id = id
        this.label = label
        this.currencyCode = currencyCode
        this.debt = debt
        this.resolved = resolved
        this.transaction = transaction
        this.group = group
        this.debtor = debtor
        this.creditor = creditor
    }
}

