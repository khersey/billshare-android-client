package com.cleganeBowl2k18.trebuchet.presentation.common.view

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.cleganeBowl2k18.trebuchet.presentation.BillShareApp
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        val fragmentTransaction = this.fragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment)
        fragmentTransaction.commit()
    }

    protected val mApplicationComponent: ApplicationComponent
        get() = (application as BillShareApp).mApplicationComponent
}