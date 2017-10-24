package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.GroupCreator
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewGroup
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.view.CreateGroupView
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-21.
 */
@PerActivity
class CreateGroupPresenter @Inject constructor(private val mCreateNewGroup: CreateNewGroup,
                                               private val mGetUser: GetUser) :
        Presenter(mCreateNewGroup, mGetUser) {

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

    fun getUser(id: Long) {
        mGetUser.execute(GetUserObserver(), id)
    }

    fun onSaveGroup(group: Group) {
        mCreateNewGroup.execute(CreateGroupObserver(), group)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mCreateGroupView!!.showError(it) }
    }

    private inner class CreateGroupObserver : DisposableObserver<Group>() {
        override fun onNext(group: Group) {
            mCreateGroupView!!.groupCreated()
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

    private inner class GetUserObserver : DisposableObserver<User>() {
        override fun onNext(user: User) {
            Log.i("ReturnedUser", user.toString())

            mCreateGroupView!!.userFetched(user)
        }

        override fun onComplete() {
            Log.i("ReturnedUser", "COMPLETED")
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }

}