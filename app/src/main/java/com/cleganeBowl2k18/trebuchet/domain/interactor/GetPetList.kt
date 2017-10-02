package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.data.repository.PetRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor

import io.reactivex.Observable
import javax.inject.Inject

class GetPetList @Inject constructor(private val mRepository: PetRepository,
                                     threadExecutor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread) :
        UseCase<List<Pet>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<Pet>> {
        return mRepository.petsAvailable()
    }
}