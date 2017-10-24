package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.entity.User
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Interface for accessing the User API
 */

interface UserService {

//    @get:GET("pet/findByStatus?status=available")
//    val petsAvailable: Observable<List<Pet>>

    @FormUrlEncoded
    @POST("user/")
    fun createUser(@Body user: User): Observable<User>

//    @PUT("pet")
//    fun updatePet(@Body pet: Pet): Observable<Pet>

//    @DELETE("pet/{petId}")
//    fun deletePet(@Path("petId") petId: Long): Observable<Response<Void>>

    @GET("user/{id}/")
    fun getUser( @Path("id") userId: Long): Observable<User>

    @GET("user/{id}/groups/")
    fun getUserGroups( @Path("id") userId: Long): Observable<List<Group>>

    @GET("user/{id}/transactions/")
    fun getUserTransactions( @Path("id") userId: Long): Observable<List<Transaction>>

}
