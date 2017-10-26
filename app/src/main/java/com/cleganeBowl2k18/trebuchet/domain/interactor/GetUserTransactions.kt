package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.modelAdapters.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.models.Transaction
import com.cleganeBowl2k18.trebuchet.data.repository.TransactionRepository
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import com.cleganeBowl2k18.trebuchet.presentation.view.adapter.UserCheckmarkAdapter
import io.reactivex.Observable
import javax.inject.Inject

/**
 * UseCase that takes a userId and returns all of that User's Transactions
 */
class GetUserTransactions @Inject constructor(private val mRepository: UserRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        UseCase<List<TransactionReceiver>, Long>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<List<TransactionReceiver>> {
        return mRepository.getTransactionsByUserId(params!!)
        //return mRepository.fakeAllTransactionsForUser(params!!)
    }

}
