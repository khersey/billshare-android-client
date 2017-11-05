package com.cleganeBowl2k18.trebuchet.data.modelAdapters


class TransactionCreatorTransaction {

    var user: Long = 0
    var owes: Double = 0.0
    var paid: Double = 0.0
    var label: String = ""

    constructor()

    constructor(user: Long, owes: Double, paid: Double, label: String) {
        this.user = user
        this.owes = owes
        this.paid = paid
        this.label = label
    }

}