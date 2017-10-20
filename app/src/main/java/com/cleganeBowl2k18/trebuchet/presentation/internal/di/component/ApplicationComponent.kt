package com.cleganeBowl2k18.trebuchet.presentation.internal.di.component

import android.app.Application
import android.content.Context
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.ApplicationModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.NetworkModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, RepositoryModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun application(): Application

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun groupRepository(): GroupRepository

    fun userRepository(): UserRepository


}