package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetGroupList
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.GroupView
import com.cleganeBowl2k18.trebuchet.presentation.view.view.HomeView
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-18.
 */
class GroupPresenter @Inject constructor(private val mGetGroupList: GetGroupList) :
        Presenter(mGetGroupList) {

    private var mGroupView: GroupView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as GroupView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mGroupView = null
    }

    fun setView(@NonNull groupView: GroupView) {
        this.mGroupView = groupView
    }

    fun fetchGroups(): List<Group> {

        mGetGroupList.execute(GroupListObserver(), null)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mGroupView!!.showError(it) }
    }

    private inner class GroupListObserver : DisposableObserver<List<Group>>() {

        override fun onNext(groups: List<Group>) {
            mGroupView!!.hideProgress()
            mGroupView!!.showGroups(groups)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            mGroupView!!.hideProgress()
            onObserverError(error)
            mGroupView!!.showGroups()
        }
    }
}