package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionSummaryReceiver

/**
 * Created by khersey on 2017-11-22.
 */
interface DashboardView {

    fun showError(message: String)

    fun summaryReceived(summary: TransactionSummaryReceiver)

}