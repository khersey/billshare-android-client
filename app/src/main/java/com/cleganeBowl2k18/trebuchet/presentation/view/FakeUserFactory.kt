package com.cleganeBowl2k18.trebuchet.presentation.view

import com.cleganeBowl2k18.trebuchet.data.entity.User

/**
 * Generates fake User objects
 */
class FakeUserFactory {
    var id: Long = 0

    constructor()

    fun generateUser(name : String) : User {
        id += 1
        return User(id-1, null, "someEmail@somewhere.com", name, "Johnson")
    }
}