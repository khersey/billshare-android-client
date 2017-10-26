package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionCreator
import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.repository.TransactionRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-23.
 */
class CreateNewTransaction @Inject constructor(private val mRepository: TransactionRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread) :
        UseCase<TransactionReceiver, TransactionCreator>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: TransactionCreator?): Observable<TransactionReceiver> {
        // return mRepository.allTransactionsForUser(params!!)
        return mRepository.createTransaction(params!!)
    }
}
