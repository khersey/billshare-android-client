package com.cleganeBowl2k18.trebuchet.data.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-11-08.
 */
class TransactionResolver {
    @SerializedName("transaction")
    var id: Long = 0
    @SerializedName("transaction_line_items")
    var transactionLine: MutableList<TransactionResolverLineItem> = mutableListOf()

    constructor()

    constructor(transactionId: Long, lineItemId: Long) {
        this.id = transactionId
        val lineItem = TransactionResolverLineItem(lineItemId)
        transactionLine = mutableListOf(lineItem)
    }
}
