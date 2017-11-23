package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionSummaryReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
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
class DashboardPresenter @Inject constructor(private val mGetTransactionsSummary: GetTransactionsSummary) :
        Presenter(mGetTransactionsSummary) {

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


}