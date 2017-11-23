package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionSummaryReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.data.network.UserService
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

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

    fun getTransactionsByUserId(id: Long) : Observable<List<TransactionReceiver>> {
        return userService.getUserTransactions(id)
    }

    fun getTransactionsSummary(id: Long) : Observable<TransactionSummaryReceiver> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val cal: Calendar = Calendar.getInstance()

        val today: Date = cal.time
        val lastDate = dateFormat.format(today)

        cal.add(Calendar.MONTH, 1)
        val end: Date = cal.time
        val firstDate = dateFormat.format(end)

        return userService.getUserTransactionsSummary(id, lastDate, firstDate)
    }

    fun getGroupBalance(userId: Long, groupId: Long) : Observable<Double> {
        return userService.getGroupBalance(userId, groupId)
    }

}
