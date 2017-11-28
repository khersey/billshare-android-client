package com.cleganeBowl2k18.trebuchet.data.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by khersey on 2017-11-22.
 */
class TransactionSummaryReceiver {
    @SerializedName("date_start")
    var firstDate: String = ""
    @SerializedName("date_end")
    var lastDate: String = ""
    @SerializedName("total transactions")
    var transactionCount: Int = 0
    var debt: Double = 0.0
    var credit: Double = 0.0

    constructor()

    constructor(firstDate: String, lastDate: String, transactionCount: Int, debt: Double, credit: Double) {
        this.firstDate = firstDate
        this.lastDate = lastDate
        this.transactionCount = transactionCount
        this.debt = debt
        this.credit = credit
    }

}