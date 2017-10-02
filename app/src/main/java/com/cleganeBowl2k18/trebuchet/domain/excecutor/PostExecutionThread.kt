package com.cleganeBowl2k18.trebuchet.domain.excecutor

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler
}
