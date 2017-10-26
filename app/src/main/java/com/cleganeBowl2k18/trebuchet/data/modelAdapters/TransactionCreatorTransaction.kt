package com.cleganeBowl2k18.trebuchet.data.modelAdapters


class TransactionCreatorTransaction {

    var payer: Long = 0
    var owes: Double = 0.0
    var paid: Double = 0.0

    constructor()

    constructor(payer: Long, owes: Double, paid: Double) {
        this.payer = payer
        this.owes = owes
        this.paid = paid
    }
}