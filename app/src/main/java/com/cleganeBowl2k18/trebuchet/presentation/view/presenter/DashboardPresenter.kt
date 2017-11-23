package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.DashboardView
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-22.
 */
@PerActivity
class DashboardPresenter @Inject constructor() :
        Presenter() {

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


}