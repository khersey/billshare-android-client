package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewGroup
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewTransaction
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUserGroups
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateTransactionView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-23.
 */
class CreateTransactionPresenter @Inject constructor(private val mGetUserGroups: GetUserGroups,
                                                     private val mGetUser: GetUser) :
        Presenter(mGetUserGroups, mGetUser) {

    // REFERENCE TO ACTIVITY
    private var mCreateTransactionView: CreateTransactionView? = null

    // EXECUTE USE CASES
    fun getGroupsByUserId(id: Long) {
        mGetUserGroups.execute(GetGroupsByUserIdObserver(), id)
    }

    // USE CASE OBSERVERS
    private inner class GetGroupsByUserIdObserver : DisposableObserver<List<Group>>() {
        override fun onNext(groups: List<Group>) {
            mCreateTransactionView!!.groupsFetched(groups)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

    // get user
    fun getUser(id: Long) {
        mGetUser.execute(GetUserObserver(), id)
    }

    private inner class GetUserObserver : DisposableObserver<User>() {
        override fun onNext(user: User) {
            mCreateTransactionView!!.userFetched(user)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }


    private fun onObserverError(error: Throwable) {
        error.message?.let { mCreateTransactionView!!.showError(it) }
    }
}