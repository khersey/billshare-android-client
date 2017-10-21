package com.cleganeBowl2k18.trebuchet.presentation.view

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.entity.User

/**
 * Generates fake Transaction objects and their dependencies
 */
class FakeTransactionFactory {

    private val groupFactory: FakeGroupFactory = FakeGroupFactory()
    private var id : Long = 0

    constructor()

    fun generateTransaction(name : String, amount: Double, groupName: String) : Transaction {
        val group: Group = groupFactory.generateGroup(groupName, listOf("Tom", "Bob", "Ian", "Jack"))

        id += 1
        return Transaction(id-1, group, name, amount, mutableMapOf<User,Double>(), mutableMapOf<User,Double>())
    }
}