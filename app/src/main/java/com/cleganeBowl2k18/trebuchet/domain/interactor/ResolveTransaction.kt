package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionResolver
import com.cleganeBowl2k18.trebuchet.data.repository.TransactionRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/*
 * Created by khersey on 2017-11-08.
 */
class ResolveTransaction @Inject constructor(private val mRepository: TransactionRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread) :
        UseCase<TransactionReceiver, TransactionResolver>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: TransactionResolver?): Observable<TransactionReceiver> {
        return mRepository.resolveTransaction(params!!)
    }
}
