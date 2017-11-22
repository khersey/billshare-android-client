package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * View interface for LoginActivity
 */
interface LoginView {

    fun loginSuccess(user: User)

    fun loginError(message: String)

    fun createAccountError(message: String)

    fun showError(message: String)
}