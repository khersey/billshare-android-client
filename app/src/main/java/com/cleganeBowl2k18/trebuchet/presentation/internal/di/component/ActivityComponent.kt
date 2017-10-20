package com.cleganeBowl2k18.trebuchet.presentation.internal.di.component

import com.cleganeBowl2k18.trebuchet.presentation.internal.di.module.ActivityModule
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.LoginActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.MainActivity
import com.cleganeBowl2k18.trebuchet.presentation.view.fragment.GroupFragment
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(fragment: GroupFragment)

    fun inject(activity: MainActivity)

    fun inject(activity: LoginActivity)

}