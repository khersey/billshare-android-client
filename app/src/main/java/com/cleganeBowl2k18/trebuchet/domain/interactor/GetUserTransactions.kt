package com.cleganeBowl2k18.trebuchet.domain.interactor

import android.util.Log
import com.cleganeBowl2k18.trebuchet.data.models.request.TransactionReceiver
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
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
        Log.i("API_CALL", "calling GET /user/${params!!}/transactions")
        return mRepository.getTransactionsByUserId(params!!)
    }

}
