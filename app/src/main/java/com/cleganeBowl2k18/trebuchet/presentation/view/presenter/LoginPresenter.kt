package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import com.cleganeBowl2k18.trebuchet.domain.interactor.GetGroup
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.domain.interactor.ResolveTransaction
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-21.
 */
@PerActivity
class LoginPresenter @Inject constructor(private val mGetGroup: GetGroup,
                                         private val mGetUser: GetUser,
                                         private val mResolveTransaction: ResolveTransaction):
        Presenter(mGetGroup, mGetUser, mResolveTransaction) {
}