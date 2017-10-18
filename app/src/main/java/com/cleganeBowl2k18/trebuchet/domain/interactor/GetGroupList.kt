package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by khersey on 2017-10-15.
 */
class GetGroupList @Inject constructor(private val mRepository: GroupRepository,
                                       threadExecutor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread) :
        UseCase<List<Group>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<Group>> {
        return mRepository.groupsAvailable()
    }
}