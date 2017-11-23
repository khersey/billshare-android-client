package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-22.
 */
class GetGroupBalance @Inject constructor(private val mRepository: UserRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread) :
        UseCase<Double, List<Long>>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: List<Long>?): Observable<Double> {
        return mRepository.getGroupBalance(params!![0], params!![1])
    }
}