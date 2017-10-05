package com.cleganeBowl2k18.trebuchet.presentation.internal.di.component

import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.ActivityModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.HomeActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.LoginActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: HomeActivity)
    fun inject(activity: LoginActivity)
}