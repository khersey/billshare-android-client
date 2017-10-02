package com.cleganeBowl2k18.trebuchet.presentation.common.executor

import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UIThread @Inject
internal constructor() : PostExecutionThread {

    override val scheduler: Scheduler = AndroidSchedulers.mainThread()
}
