package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by khersey on 2017-10-06.
 */

interface UserStoreService {
//    @get:GET("pet/findByStatus?status=available")
//    val petsAvailable: Observable<List<Pet>>

    @FormUrlEncoded
    @POST("user")
    fun createUser(@Body user: User): Observable<User>

//    @PUT("pet")
//    fun updatePet(@Body pet: Pet): Observable<Pet>

//    @DELETE("pet/{petId}")
//    fun deletePet(@Path("petId") petId: Long): Observable<Response<Void>>

    @GET("user/{userId}")
    fun getUser(userId: Long): Observable<User>

    @GET("user/{userId}/groups")
    fun getUserGroups(userId: Long): Observable<List<Group>>
}