package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * View interface for TransactionFragment
 */
interface TransactionView: ProgressView {

    fun showTransactions(transactions: List<Transaction>)

    fun showTransactions()

    fun showError(message: String)
}