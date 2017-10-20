package com.cleganeBowl2k18.trebuchet.presentation

import android.app.Application
import com.cleganeBowl2k18.trebuchet.BuildConfig
import com.cleganeBowl2k18.trebuchet.presentation.common.Constants
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.ApplicationComponent
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.component.DaggerApplicationComponent
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.ApplicationModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.NetworkModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.RepositoryModule
import com.squareup.leakcanary.LeakCanary

class BillShareApp : Application() {

    lateinit var mApplicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
        initializeLeakDetection()
    }

    private fun initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .repositoryModule(RepositoryModule())
                .networkModule(NetworkModule(Constants.Api.BASE_API_URL))
                .build()
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }
}