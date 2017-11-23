package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionSummaryReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Interface for accessing the User API
 */

interface UserService {

    @POST("user/")
    fun createUser(@Body user: User): Observable<User>

    @GET("user/{id}/")
    fun getUser( @Path("id") userId: Long): Observable<User>

    @GET("user/{id}/groups/")
    fun getUserGroups( @Path("id") userId: Long): Observable<List<Group>>

    @GET("user/{id}/transactions/")
    fun getUserTransactions( @Path("id") userId: Long): Observable<List<TransactionReceiver>>

    @GET("/user/{id}/transactions/summary/")
    fun getUserTransactionsSummary(@Path("id") userId: Long,
                                   @Query("date_end") lastDate: String,
                                   @Query("date_start") firstDate: String):
            Observable<TransactionSummaryReceiver>

    @GET("/user/{id}/group/{groupId}/balance")
    fun getGroupBalance(@Path("id") userId: Long, @Path("groupId") groupId: Long): Observable<Double>

}
