package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.request.DashboardReceiver
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionSummaryReceiver
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetRecentActivity
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetTransactionsSummary
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.DashboardView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-22.
 */
@PerActivity
class DashboardPresenter @Inject constructor(private val mGetTransactionsSummary: GetTransactionsSummary,
                                             private val mGetRecentActivity: GetRecentActivity) :
        Presenter(mGetTransactionsSummary, mGetRecentActivity) {

    private var mDashboardView: DashboardView? = null

    fun setView(@NonNull dashboardView: DashboardView) {
        this.mDashboardView = dashboardView
    }

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as DashboardView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mDashboardView = null
    }

    fun getTransactionsSummary(id: Long) {
        mGetTransactionsSummary.execute(TransactionSummaryListener(), id)
    }

    fun getRecentActivity(id: Long) {
        mGetRecentActivity.execute(RecentActivityListener(), id)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mDashboardView!!.showError(it) }
    }

    private inner class TransactionSummaryListener : DisposableObserver<TransactionSummaryReceiver>() {

        override fun onNext(summary: TransactionSummaryReceiver) {
            mDashboardView!!.summaryReceived(summary)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

    private inner class RecentActivityListener : DisposableObserver<DashboardReceiver>() {

        override fun onNext(recentData: DashboardReceiver) {
            // convert transaction response object to transaction model
            val recentTransactions = recentData.transactions.map { receiver -> receiver.toTransaction() }.toMutableList()

            // By default transaction group only contains the group's id but we need the
            // group label for the card, so associate the full groups to the appropriate
            // transactions.
            for (transaction in recentTransactions) {
                val group: Group? = recentData.groups.find { group -> group.externalId == transaction.group!!.externalId }
                if (group != null) {
                    transaction.group = group
                }
            }

            mDashboardView!!.transactionsReceived(recentTransactions)
            mDashboardView!!.groupsReceived(recentData.groups.toMutableList())
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }


}