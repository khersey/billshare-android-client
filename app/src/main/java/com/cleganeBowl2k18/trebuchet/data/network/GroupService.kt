package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.GroupCreator
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface for accessing the Groups API
 */
interface GroupService {

    @get:GET("group/all/")
    val allGroups: Observable<List<Group>>
    
    @POST("group/")
    fun createGroup(@Body group: GroupCreator): Observable<Group>

    @PUT("group/")
    fun updateGroup(@Body group: GroupCreator): Observable<Group>

    @DELETE("group/{id}/")
    fun deleteGroup(@Path("id") groupId: Long): Observable<Response<Void>>

    @GET("group/{id}/")
    fun getGroup(@Path("id") groupId: Long): Observable<Group>

    @GET("group/{id}/transactions/")
    fun getTransactionsByGroup(@Path("id") groupId: Long): Observable<List<Transaction>>


}