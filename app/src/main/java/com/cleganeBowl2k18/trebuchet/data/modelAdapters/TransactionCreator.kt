package com.cleganeBowl2k18.trebuchet.data.modelAdapters

import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.google.gson.annotations.SerializedName

class TransactionCreator {
    var total: Double = 0.0
    @SerializedName("currency_code")
    var currencyCode: String? = null
    var label: String? = null
    var group: Long? = null
    var creator: Long? = null
    var transactions: List<TransactionCreatorTransaction>? = listOf()

    constructor()

    constructor(total: Double, currency_code: String, label: String, group: Long, creator: Long, transactions: List<TransactionCreatorTransaction>) {
        this.total = total
        this.currencyCode = currency_code
        this.label = label
        this.group = group
        this.creator = creator
        this.transactions = transactions
    }

    constructor(transaction: Transaction, creator: Long) {
        this.total = transaction.amount.toDouble() * 0.01
        this.currencyCode = transaction.currencyCode
        this.label = transaction.label
        this.group = transaction.group!!.externalId
        this.creator = creator
        var list: MutableList<TransactionCreatorTransaction> = mutableListOf()

        for (user in transaction!!.group!!.users!!) {
            var tc = TransactionCreatorTransaction()
            tc.payer = user.externalId
            if (transaction!!.oweSplit!!.keys.contains(tc.payer)) {
                tc.owes = transaction!!.oweSplit!![user.externalId]!!.toDouble() * 0.01
            }
            if (transaction!!.paySplit!!.keys.contains(tc.payer)) {
                tc.paid = transaction!!.paySplit!![user.externalId]!!.toDouble() * 0.01
            }

            if (tc.owes > 0 || tc.paid > 0) {
                list.add(tc)
            }
        }

        this.transactions = list
    }
}