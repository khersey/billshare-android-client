package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface for accessing the Groups API
 */
interface GroupService {
    @get:GET("group/all")
    val allGroups: Observable<List<Group>>
    
    @POST("group")
    fun createGroup(@Body group: Group): Observable<Group>

    @PUT("group")
    fun updateGroup(@Body group: Group): Observable<Group>

    @DELETE("group/{groupId}")
    fun deleteGroup(@Path("groupId") groupId: Long): Observable<Response<Void>>

    @GET("group/{groupId}")
    fun getGroup(@Path("groupId") groupId: Long): Observable<Group>


}