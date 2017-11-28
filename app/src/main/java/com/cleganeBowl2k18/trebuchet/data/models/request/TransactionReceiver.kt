package com.cleganeBowl2k18.trebuchet.data.models.request

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.google.gson.annotations.SerializedName


class TransactionReceiver {

    var id: Long = 0
    var label: String = ""
    @SerializedName("created_date")
    var createdDate: String = "" // TODO: change this to an actual date
    @SerializedName("updated_date")
    var updatedDate: String = "" // TODO: change this to an actual date
    var group: Long = 0
    var creator: Long = 0
    @SerializedName("transaction_line_items")
    var transactions: List<TransactionLineItem>? = null

    constructor(id: Long, label: String, createdDate: String, updatedDate: String, group: Long, creator: Long, transactions: List<TransactionLineItem>?) {
        this.id = id
        this.label = label
        this.createdDate = createdDate
        this.updatedDate = updatedDate
        this.group = group
        this.creator = creator
        this.transactions = transactions
    }

    fun toTransaction(): Transaction {
        var total: Double = 0.0
        val paySplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()
        val oweSplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()
        val resolved: MutableMap<Long, Boolean> = mutableMapOf<Long, Boolean>()
        val lineItemMap: MutableMap<Long, Long> = mutableMapOf<Long,Long>()

        for (lineItem in this.transactions!!) {
            if (paySplit[lineItem.creditor] != null) {
                val currentVal: Long = paySplit[lineItem.creditor]!!
                paySplit[lineItem.creditor] = currentVal + (lineItem.debt * 100).toLong()
            } else {
                paySplit.put(lineItem.creditor, (lineItem.debt * 100).toLong())
            }

            if (oweSplit[lineItem.debtor] != null) {
                val currentVal: Long = oweSplit[lineItem.debtor]!!
                oweSplit[lineItem.debtor] = currentVal + (lineItem.debt * 100).toLong()
            } else {
                oweSplit.put(lineItem.debtor, (lineItem.debt * 100).toLong())
            }

            resolved[lineItem.debtor] = lineItem.resolved
            lineItemMap[lineItem.debtor] = lineItem.id
            total += lineItem.debt
        }

        paySplit.keys.map {
            userId ->
            if (lineItemMap[userId] == null) {
                lineItemMap[userId] = -1
            }
            if (resolved[userId] == null) {
                resolved[userId] = true
            }
        }

        return Transaction(
                id,
                Group(group, null, null),
                label,
                (total * 100).toLong(),
                "CAD",
                creator,
                Constants.SPLIT_BY_AMOUNT,
                paySplit,
                oweSplit,
                resolved,
                lineItemMap
        )
    }
}
