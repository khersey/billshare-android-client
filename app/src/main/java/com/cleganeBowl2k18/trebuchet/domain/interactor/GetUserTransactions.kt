package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Transaction
import com.cleganeBowl2k18.trebuchet.data.repository.TransactionRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * UseCase that takes a userId and returns all of that User's Transactions
 */
class GetUserTransactions @Inject constructor(private val mRepository: TransactionRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        UseCase<List<Transaction>, Long>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<List<Transaction>> {
        // return mRepository.allTransactionsForUser(params!!)
        return mRepository.fakeAllTransactionsForUser(params!!)
    }

}
