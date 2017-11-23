package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.models.request.LoginRequest
import com.cleganeBowl2k18.trebuchet.data.models.User
import com.cleganeBowl2k18.trebuchet.data.repository.AuthRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-21.
 */
class Login @Inject constructor(private val mRepository: AuthRepository,
                                  threadExecutor: ThreadExecutor,
                                  postExecutionThread: PostExecutionThread) :
        UseCase<User, LoginRequest>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: LoginRequest?): Observable<User> {
        return mRepository.login(params!!)
    }
}