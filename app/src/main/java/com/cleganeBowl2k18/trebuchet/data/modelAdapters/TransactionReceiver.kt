package com.cleganeBowl2k18.trebuchet.data.modelAdapters

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction


class TransactionReceiver {
    var bill: TransactionReceiverTransaction? = null
    var transactions: List<TransactionLineItem>? = null

    constructor(bill: TransactionReceiverTransaction, transactions: List<TransactionLineItem>) {
        this.bill = bill
        this.transactions = transactions
    }

    fun toTransaction(): Transaction {
        var total: Double = 0.0
        var paySplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()
        var oweSplit: MutableMap<Long, Long> = mutableMapOf<Long,Long>()

        for (lineItem in this.transactions!!) {
            paySplit.put(lineItem.payer, (lineItem.debt!!["amount"]!!.toDouble()*100).toLong())
            total += lineItem.debt!!["amount"]!!.toDouble()
        }

        for (lineItem in this.transactions!!) {
            oweSplit.put(lineItem.payee, ((total/(this.transactions!!.size)) *100).toLong())
        }

        return Transaction(
                this.bill!!.id,
                Group(this.bill!!.group, null, null),
                this.bill!!.label,
                (total * 100).toLong(),
                "CAD",
                false,
                paySplit,
                oweSplit
        )
    }
}

//[{
//    "bill":
//        {"id": 1, "label": "testLabel", "group": 1, "creator": 1},
//    "transactions": [
//    {"id": 1, "label": "", "bill": 1, "group": 1, "debt":{
//        "amount": "100.00", "currency": "CAD"
//    },"payee": 2, "payer": 1, "resolved": false}
//    ]
//}]
