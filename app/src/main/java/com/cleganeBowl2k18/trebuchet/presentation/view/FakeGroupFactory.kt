package com.cleganeBowl2k18.trebuchet.presentation.view

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User

/**
 * Created by khersey on 2017-10-18.
 */
class FakeGroupFactory {
    var userFactory: FakeUserFactory = FakeUserFactory()
    var id : Long = 0

    constructor()

    fun generateGroup(name : String, userNames: List<String>) : Group {
        var users : List<User> = userNames.map { uName -> userFactory.generateUser(uName) }
        id += 1
        return Group(id-1, name, "available", users)
    }
}