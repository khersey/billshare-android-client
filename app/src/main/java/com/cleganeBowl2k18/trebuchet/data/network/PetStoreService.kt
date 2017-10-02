package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import io.reactivex.Observable
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetStoreService {

    @get:GET("pet/findByStatus?status=available")
    val petsAvailable: Observable<List<Pet>>

    @POST("pet")
    fun createPet(@Body pet: Pet): Observable<Pet>

    @PUT("pet")
    fun updatePet(@Body pet: Pet): Observable<Pet>

    @DELETE("pet/{petId}")
    fun deletePet(@Path("petId") petId: Long): Observable<Response<Void>>

    @GET("pet/{petId}")
    fun getPet(petId: Long): Observable<Pet>
}