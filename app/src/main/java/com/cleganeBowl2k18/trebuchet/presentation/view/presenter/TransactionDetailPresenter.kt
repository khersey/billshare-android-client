package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionResolver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetGroup
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.ResolveTransaction
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.TransactionDetailsView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-08.
 */
@PerActivity
class TransactionDetailPresenter @Inject constructor(private val mGetGroup: GetGroup,
                                                     private val mGetUser: GetUser,
                                                     private val mResolveTransaction: ResolveTransaction):
        Presenter(mGetGroup, mGetUser, mResolveTransaction) {

    var mTransactionDetailsView: TransactionDetailsView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as TransactionDetailsView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mTransactionDetailsView = null
    }

    fun setView(@NonNull transactionDetailsView: TransactionDetailsView) {
        this.mTransactionDetailsView = transactionDetailsView
    }

    fun getGroup(groupId: Long) {
        mGetGroup.execute(GetGroupObserver(), groupId)
    }

    fun getUser(id: Long) {
        mGetUser.execute(GetUserObserver(), id)
    }

    fun resolveTransaction(transactionId: Long, lineItemId: Long) {
        mResolveTransaction.execute(ResolveTransactionObserver(), TransactionResolver(transactionId, lineItemId))
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mTransactionDetailsView!!.showError(it) }
    }

    private inner class GetGroupObserver : DisposableObserver<Group>() {
        override fun onNext(group: Group) {
            mTransactionDetailsView!!.groupReturned(group)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

    private inner class GetUserObserver : DisposableObserver<User>() {
        override fun onNext(user: User) {
            mTransactionDetailsView!!.userReturned(user)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

    private inner class ResolveTransactionObserver : DisposableObserver<TransactionReceiver>() {
        override fun onNext(transactionReceiver: TransactionReceiver) {
            mTransactionDetailsView!!.transactionUpdated(transactionReceiver)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }
}