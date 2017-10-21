package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * Created by khersey on 2017-10-20.
 */
interface TransactionView: ProgressView {

    fun showTransactions(transactions: List<Transaction>)

    fun showTransactions()

    fun showError(message: String)
}