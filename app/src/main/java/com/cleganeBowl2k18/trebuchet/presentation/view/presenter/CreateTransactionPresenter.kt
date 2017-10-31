package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionCreator
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewTransaction
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUserGroups
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateTransactionView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-23.
 */
@PerActivity
class CreateTransactionPresenter @Inject constructor(private val mGetUserGroups: GetUserGroups,
                                                     private val mGetUser: GetUser,
                                                     private val mCreateNewTransaction: CreateNewTransaction) :
        Presenter(mGetUserGroups, mGetUser, mCreateNewTransaction) {

    // REFERENCE TO ACTIVITY
    private var mCreateTransactionView: CreateTransactionView? = null

    fun setView(@NonNull createTransactionView: CreateTransactionView) {
        this.mCreateTransactionView = createTransactionView
    }

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as CreateTransactionView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mCreateTransactionView = null
    }

    // EXECUTE USE CASES
    fun getGroupsByUserId(id: Long) {
        mGetUserGroups.execute(GetGroupsByUserIdObserver(), id)
    }

    fun createTransaction(transaction: Transaction) {
        mCreateNewTransaction.execute(CreateTranactionObserver(), TransactionCreator(transaction, 1)) // TODO: fix this
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

    private inner class CreateTranactionObserver : DisposableObserver<TransactionReceiver>() {
        override fun onNext(transactionReceiver: TransactionReceiver) {
            mCreateTransactionView!!.transactionCreated(transactionReceiver)
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