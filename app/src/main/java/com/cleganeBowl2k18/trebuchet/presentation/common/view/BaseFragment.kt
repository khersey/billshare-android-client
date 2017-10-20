package com.cleganeBowl2k18.trebuchet.presentation.common.view


import android.support.v4.app.Fragment
import com.cleganeBowl2k18.trebuchet.presentation.BillShareApp
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.ApplicationComponent

/**
 * Created by khersey on 2017-10-19.
 */
abstract class BaseFragment: Fragment() {

    protected val mApplicationComponent: ApplicationComponent
        get() = ((activity as BaseActivity).application as BillShareApp).mApplicationComponent

}