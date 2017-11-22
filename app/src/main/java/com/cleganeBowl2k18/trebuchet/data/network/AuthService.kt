package com.cleganeBowl2k18.trebuchet.data.network

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.CreateUserRequest
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.LoginRequest
import com.cleganeBowl2k18.trebuchet.data.models.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by khersey on 2017-11-21.
 */
interface AuthService {

    @POST("auth/login/")
    fun login(@Body loginRequest: LoginRequest): Observable<User>

    @POST("auth/create/")
    fun createUser(@Body createUserRequest: CreateUserRequest): Observable<User>

    @POST("auth/logout/")
    fun logout(): Observable<Response<Void>>
}