package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.repository.AuthRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by khersey on 2017-11-21.
 */
class Logout @Inject constructor(private val mRepository: AuthRepository,
                                threadExecutor: ThreadExecutor,
                                postExecutionThread: PostExecutionThread) :
        UseCase<Response<Void>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<Response<Void>> {
        return mRepository.logout()
    }
}
