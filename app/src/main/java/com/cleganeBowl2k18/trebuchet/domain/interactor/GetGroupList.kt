package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Group
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Use that fetches all groups a User is a part of
 */
class GetGroupList @Inject constructor(private val mRepository: UserRepository,
                                       threadExecutor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread) :
        UseCase<List<Group>, Long>(threadExecutor, postExecutionThread) {

    // TODO: make this fetch by User.id
    override fun buildUseCaseObservable(params: Long?): Observable<List<Group>> {
        return mRepository.getGroupsByUserId(params!!)
    }
}