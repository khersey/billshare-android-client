package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.repository.PetRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class DeletePet @Inject constructor(private val mRepository: PetRepository,
                                    threadExecutor: ThreadExecutor,
                                    postExecutionThread: PostExecutionThread) :
        UseCase<Response<Void>, Long>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Long?): Observable<Response<Void>> {
        return mRepository.deletePet(params!!)
    }
}