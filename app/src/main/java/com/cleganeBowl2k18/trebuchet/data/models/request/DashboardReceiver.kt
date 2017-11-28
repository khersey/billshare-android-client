package com.cleganeBowl2k18.trebuchet.data.models.request

import com.cleganeBowl2k18.trebuchet.data.models.Group

/**
 * Created by khersey on 2017-11-27.
 */
class DashboardReceiver {
    var groups: List<Group> = listOf()
    var transactions: List<TransactionReceiver> = listOf()

    constructor(groups: List<Group>, transactions: List<TransactionReceiver>) {
        this.groups = groups
        this.transactions = transactions
    }
}