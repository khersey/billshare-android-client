package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionCreator
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionResolver
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface TransactionService {

    @GET("transaction/{id}/")
    fun getTransactionById(@Path("id") transactionId: Long): Observable<TransactionReceiver>

    @POST("transaction/")
    fun createTransaction(@Body transaction: TransactionCreator): Observable<TransactionReceiver>

    @PUT("transaction/")
    fun resolveTransaction(@Body transaction: TransactionResolver): Observable<TransactionReceiver>

    @PUT("transaction/")
    fun updateTransaction(@Body transaction: Transaction): Observable<TransactionReceiver>

    @DELETE("transaction/{id}/")
    fun deleteTransaction(@Path("id") transactionId: Long): Observable<Response<Void>>

}