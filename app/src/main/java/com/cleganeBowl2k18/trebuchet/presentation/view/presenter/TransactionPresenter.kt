package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUserGroups
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUserTransactions
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * TransactionFragment Presenter
 */
@PerActivity
class TransactionPresenter @Inject constructor(private val mGetTransactionList: GetUserTransactions,
                                               private val mGetUserGroups: GetUserGroups) :
        Presenter(mGetTransactionList, mGetUserGroups) {

    private var mTransactionView: TransactionView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as TransactionView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mTransactionView = null
    }

    fun setView(@NonNull transactionView: TransactionView) {
        this.mTransactionView = transactionView
    }

    fun fetchTransactionsByUser(userId: Long) {
        mGetTransactionList.execute(TransactionListObserver(), userId)
    }

    fun fetchGroupsByUserId(userId: Long) {
        mGetUserGroups.execute(GroupListObserver(), userId)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mTransactionView!!.showError(it) }
    }

    private inner class TransactionListObserver : DisposableObserver<List<TransactionReceiver>>() {

        override fun onNext(transactions: List<TransactionReceiver>) {
            mTransactionView!!.hideProgress()
            var list: MutableList<Transaction> = mutableListOf()
            for (tr in transactions) {
                list.add(tr.toTransaction())
            }
            mTransactionView!!.showTransactions(list)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            mTransactionView!!.hideProgress()
            onObserverError(error)
            mTransactionView!!.showTransactions()
        }
    }

    private inner class GroupListObserver : DisposableObserver<List<Group>>() {

        override fun onNext(groups: List<Group>) {
            mTransactionView!!.hideProgress()
            mTransactionView!!.returnedGroups(groups)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            mTransactionView!!.hideProgress()
            onObserverError(error)
            mTransactionView!!.returnedGroups()
        }
    }
}