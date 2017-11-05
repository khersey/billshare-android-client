package com.cleganeBowl2k18.trebuchet.presentation.view

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction

/**
 * Generates fake Transaction objects and their dependencies
 */
class FakeTransactionFactory {

    private val groupFactory: FakeGroupFactory = FakeGroupFactory()
    private var id : Long = 0

    constructor()

    fun generateTransaction(name : String, amount: Long, groupName: String) : Transaction {
        val group: Group = groupFactory.generateGroup(groupName, listOf("Tom", "Bob", "Ian", "Jack"))

        id += 1
        return Transaction(id-1, group, name, amount, "CAD", 1, false, mutableMapOf<Long,Long>(), mutableMapOf<Long,Long>())
    }
}