package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.models.Group
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use that fetches all groups a User is a part of
 */
class GetGroup @Inject constructor(private val mRepository: GroupRepository,
                                   threadExecutor: ThreadExecutor,
                                   postExecutionThread: PostExecutionThread) :
        UseCase<Group, Long>(threadExecutor, postExecutionThread) {

    // TODO: make this fetch by User.id
    override fun buildUseCaseObservable(params: Long?): Observable<Group> {
        return mRepository.getGroup(params!!)
    }
}