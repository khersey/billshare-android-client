package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.entity.GroupCreator
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-21.
 */
class CreateNewGroup @Inject constructor(private val mRepository: GroupRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread) :
        UseCase<Group, GroupCreator>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: GroupCreator?): Observable<Group> {
        // return mRepository.allTransactionsForUser(params!!)
        return mRepository.createGroup(params!!)
    }
}
