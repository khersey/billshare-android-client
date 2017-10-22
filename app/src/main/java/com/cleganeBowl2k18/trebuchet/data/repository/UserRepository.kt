package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.data.network.UserStoreService
import io.reactivex.Observable

/**
 * Facilitates User API calls
 */

class UserRepository(private val userStoreService: UserStoreService) {

    fun getUserById(id: Long) : Observable<User> = userStoreService.getUser(id)

}