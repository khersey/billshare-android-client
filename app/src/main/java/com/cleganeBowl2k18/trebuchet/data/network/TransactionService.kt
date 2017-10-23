package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface TransactionService {

    @POST("transaction/")
    fun createTransaction(@Body transaction: Transaction): Observable<Transaction>

    @PUT("transaction/")
    fun updateTransaction(@Body transaction: Transaction): Observable<Transaction>

    @DELETE("transaction/{transactionId}/")
    fun deleteTransaction(@Path("transactionId") transactionId: Long): Observable<Response<Void>>

}