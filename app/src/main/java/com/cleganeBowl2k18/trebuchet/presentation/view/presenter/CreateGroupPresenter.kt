package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewGroup
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateGroupView
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-21.
 */
@PerActivity
class CreateGroupPresenter @Inject constructor(private val mCreateNewGroup: CreateNewGroup) :
        Presenter(mCreateNewGroup) {

    private var mCreateGroupView: CreateGroupView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as CreateGroupView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mCreateGroupView = null
    }

    fun setView(@NonNull createGroupView: CreateGroupView) {
        this.mCreateGroupView = createGroupView
    }

}