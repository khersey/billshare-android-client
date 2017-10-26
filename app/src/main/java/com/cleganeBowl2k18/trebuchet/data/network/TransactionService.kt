package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionCreator
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface TransactionService {

    @GET("transaction/{id}/")
    fun getTransactionById(@Path("id") transactionId: Long): Observable<TransactionReceiver>

    @POST("transaction/")
    fun createTransaction(@Body transaction: TransactionCreator): Observable<TransactionReceiver>

    @PUT("transaction/")
    fun updateTransaction(@Body transaction: Transaction): Observable<TransactionReceiver>

    @DELETE("transaction/{transactionId}/")
    fun deleteTransaction(@Path("transactionId") transactionId: Long): Observable<Response<Void>>

}