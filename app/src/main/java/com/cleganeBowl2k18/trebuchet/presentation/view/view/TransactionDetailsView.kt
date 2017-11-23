package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.models.User

/**
 * Created by khersey on 2017-11-08.
 */
interface TransactionDetailsView {

    fun groupReturned(group: Group)

    fun userReturned(user: User)

    fun transactionUpdated(transactionReceiver: TransactionReceiver)

    fun showError(message: String)
}