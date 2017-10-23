package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.data.network.UserService
import io.reactivex.Observable

/**
 * Facilitates User API calls
 */

class UserRepository(private val userService: UserService) {

    fun getUserById(id: Long) : Observable<User> = userService.getUser(id)

    fun getGroupsByUserId(id: Long) : Observable<List<Group>> {
        val result : Observable<List<Group>>? = userService.getUserGroups(id)
        if (result == null) {
            val list : List<Group> = listOf()
            return Observable.just(list)
        }
        return result
    }

    fun getTransactionsByUserId(id: Long) : Observable<List<Transaction>> = userService.getUserTransactions(id)

}
