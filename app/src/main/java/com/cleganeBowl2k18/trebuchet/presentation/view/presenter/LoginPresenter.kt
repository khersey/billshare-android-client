package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.LoginRequest
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.Login
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.LoginView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-21.
 */
@PerActivity
class LoginPresenter @Inject constructor(private val mLogin: Login,
                                         private val mGetUser: GetUser,
                                         private val mCreateUser: CreateUser):
        Presenter(mLogin, mGetUser, mCreateUser) {

    var mLoginView: LoginView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as LoginView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mLoginView = null
    }

    fun setView(@NonNull loginView: LoginView) {
        this.mLoginView = loginView
    }

    fun login(email: String, password: String) {
        mLogin.execute(LoginObserver(), LoginRequest(email, password))
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mLoginView!!.showError(it) }
    }

    private inner class LoginObserver : DisposableObserver<User>() {
        override fun onNext(user: User) {
            mLoginView!!.loginSuccess(user)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }
}