package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUserTransactions
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-20.
 */
@PerActivity
class TransactionPresenter @Inject constructor(private val mGetTransactionList: GetUserTransactions) :
        Presenter(mGetTransactionList) {

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

    fun fetchTransactions() {
        mGetTransactionList.execute(TransactionListObserver(), 1)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mTransactionView!!.showError(it) }
    }

    private inner class TransactionListObserver : DisposableObserver<List<Transaction>>() {

        override fun onNext(transactions: List<Transaction>) {
            mTransactionView!!.hideProgress()
            mTransactionView!!.showTransactions(transactions)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            mTransactionView!!.hideProgress()
            onObserverError(error)
            mTransactionView!!.showTransactions()
        }
    }
}