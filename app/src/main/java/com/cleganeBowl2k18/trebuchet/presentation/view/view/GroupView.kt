package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

/**
 * View interface for GroupFragment
 */
interface GroupView: ProgressView {

    fun showGroups(groups: List<Group>)

    fun showGroups()

    fun showError(message: String)
}