package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * Created by khersey on 2017-10-21.
 */
interface CreateGroupView: ProgressView {

    fun userFetched(user: User)

    fun showError(message: String)

    fun groupCreated()

}