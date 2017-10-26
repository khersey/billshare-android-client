package com.cleganeBowl2k18.trebuchet.data.entity

import com.cleganeBowl2k18.trebuchet.data.network.GroupService
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository


class TransactionReceiver {
    var bill: TransactionReceiverTransaction? = null
    var transactions: List<TransactionLineItem>? = null

    constructor(bill: TransactionReceiverTransaction, transactions: List<TransactionLineItem>) {
        this.bill = bill
        this.transactions = transactions
    }

    fun toTransaction(): Transaction {
        var total: Double = 0.0
        var paySplit: MutableMap<Long, Double> = mutableMapOf<Long,Double>()
        var oweSplit: MutableMap<Long, Double> = mutableMapOf<Long,Double>()

        for (lineItem in this.transactions!!) {
            paySplit.put(lineItem.payer, lineItem.debt!!.get("amount")!!.toDouble())
            total += lineItem.debt!!.get("amount")!!.toDouble()
        }

        for (lineItem in this.transactions!!) {
            oweSplit.put(lineItem.payee, (total/(this.transactions!!.size)))
        }

        return Transaction(
                this.bill!!.id,
                Group(this.bill!!.group, null, null),
                this.bill!!.label,
                total,
                paySplit,
                oweSplit
        )
    }
}
