package com.cleganeBowl2k18.trebuchet.presentation.view

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * Generates Group objects and their dependencies
 */
class FakeGroupFactory {
    var userFactory: FakeUserFactory = FakeUserFactory()
    var id : Long = 0

    constructor()

    fun generateGroup(name : String, userNames: List<String>) : Group {
        var users : List<User> = userNames.map { uName -> userFactory.generateUser(uName) }
        id += 1
        return Group(id-1, name, users)
    }
}