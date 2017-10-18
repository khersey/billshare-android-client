package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * Created by khersey on 2017-10-18.
 */
interface GroupView: ProgressView {

    fun showGroups(groups: List<Group>) {}

    fun showError(message: String)
}