package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.User
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * Created by khersey on 2017-10-23.
 */
interface CreateTransactionView: ProgressView {

    fun groupsFetched(groups: List<Group>)

    fun userFetched(user: User)

    fun showError(error: String)


}