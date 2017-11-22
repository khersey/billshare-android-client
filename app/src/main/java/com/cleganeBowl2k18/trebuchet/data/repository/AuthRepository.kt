package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.CreateUserRequest
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.LoginRequest
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.data.network.AuthService
import io.reactivex.Observable
import retrofit2.Response


/**
 * Created by khersey on 2017-11-21.
 */
class AuthRepository(private val authService: AuthService) {

    fun login(loginRequest: LoginRequest): Observable<User> {
        return authService.login(loginRequest)
    }

    fun createUser(createUserRequest: CreateUserRequest): Observable<User> {
        return authService.createUser(createUserRequest)
    }

    fun logout(): Observable<Response<Void>> {
        return authService.logout()
    }

}