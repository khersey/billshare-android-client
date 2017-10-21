package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface TransactionService {
    @GET("group/{groupId}/transaction")
    fun getTransactionsByGroup(groupId: Long): Observable<List<Transaction>>

    @GET("user/{userId}/transaction")
    fun getTransactionsByUser(userId: Long): Observable<List<Transaction>>

    @GET("transaction/{transactionId}")
    fun getTransacion(transactionId: Long): Observable<Transaction>

    @POST("transaction")
    fun createTransaction(@Body transaction: Transaction): Observable<Transaction>

    @PUT("transaction")
    fun updateTransaction(@Body transaction: Transaction): Observable<Transaction>

    @DELETE("transaction/{transactionId}")
    fun deleteTransaction(@Path("transactionId") transactionId: Long): Observable<Response<Void>>

}