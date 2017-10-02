package com.cleganeBowl2k18.trebuchet.presentation.common.presenter

import android.view.View
import com.cleganeBowl2k18.trebuchet.domain.interactor.UseCase

open class Presenter(vararg useCases: UseCase<*, *>) {

    private val useCasesList: List<UseCase<*, *>> = ArrayList()

    init {
        for (userCase in useCases) {
            useCasesList.plus(userCase)
        }
    }

    open fun onResume(view: View) {}

    open fun onPause() {
        for (userCase in useCasesList) {
            userCase.dispose()
        }
    }

    open fun onDestroy() {
    }
}