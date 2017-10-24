package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.entity.TransactionCreator
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
        UseCase<Transaction, Transaction>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Transaction?): Observable<Transaction> {
        // return mRepository.allTransactionsForUser(params!!)
        return mRepository.createTransaction(params!!)
    }
}
