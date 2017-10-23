package com.cleganeBowl2k18.trebuchet.presentation.view.presenter

import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewGroup
import com.cleganeBowl2k18.trebuchet.domain.interactor.CreateNewTransaction
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetUser
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-23.
 */
class CreateTransactionPresenter @Inject constructor(private val mCreateNewTransaction: CreateNewTransaction,
                                                     private val mGetUser: GetUser) :
        Presenter(mCreateNewTransaction, mGetUser) {



}