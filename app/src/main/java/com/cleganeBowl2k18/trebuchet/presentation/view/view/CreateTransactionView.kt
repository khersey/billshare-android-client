package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * Created by khersey on 2017-10-23.
 */
interface CreateTransactionView: ProgressView {

    fun groupsFetched(groups: List<Group>)

    fun userFetched(user: User)

    fun transactionCreated(transactionReceiver: TransactionReceiver)

    fun showError(error: String)


}