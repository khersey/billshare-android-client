package com.cleganeBowl2k18.trebuchet.data.entity

import com.google.gson.annotations.SerializedName

class TransactionCreator {
    var total: Long = 0
    @SerializedName("currency_code")
    var currencyCode: String? = null
    var label: String? = null
    var group: Long? = null
    var creator: Long? = null
    var transactions: List<TransactionCreatorTransaction>? = null

    constructor()

    constructor(total: Long, currency_code: String, label: String, group: Long, creator: Long, transactions: List<TransactionCreatorTransaction>) {
        this.total = total
        this.currencyCode = currency_code
        this.label = label
        this.group = group
        this.creator = creator
        this.transactions = transactions
    }
}