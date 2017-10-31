package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-22.
 */
class GetUser @Inject constructor(private val mRepository: UserRepository,
                                  threadExecutor: ThreadExecutor,
                                  postExecutionThread: PostExecutionThread) :
        UseCase<User, Long>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<User> {
        return mRepository.getUserById(params!!)
    }
}