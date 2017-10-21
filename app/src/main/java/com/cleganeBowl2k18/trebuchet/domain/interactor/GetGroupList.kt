package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use that fetches all groups a User is a part of
 */
class GetGroupList @Inject constructor(private val mRepository: GroupRepository,
                                       threadExecutor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread) :
        UseCase<List<Group>, Void>(threadExecutor, postExecutionThread) {

    // TODO: make this fetch by User.id
    override fun buildUseCaseObservable(params: Void?): Observable<List<Group>> {
        return mRepository.fakeGroupsAvailable()
        // return mRepository.groupsAvailable()
    }
}