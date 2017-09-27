package com.cleganeBowl2k18.trebuchet.presentation.view.home

import android.support.annotation.NonNull
import android.view.View
import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.domain.interactor.DeletePet
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetPetList
import com.cleganeBowl2k18.trebuchet.presentation.common.presenter.Presenter
import com.cleganeBowl2k18.trebuchet.presentation.internal.di.scope.PerActivity
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import javax.inject.Inject

@PerActivity
class HomePresenter @Inject constructor(private val mGetPetListUseCase: GetPetList,
                                        private val mDeletePetUseCase: DeletePet) :
        Presenter(mGetPetListUseCase, mDeletePetUseCase) {

    private var mHomeView: HomeView? = null

    override fun onResume(view: View) {
        super.onResume(view)
        setView(view as HomeView)
    }

    override fun onDestroy() {
        super.onPause()
        this.mHomeView = null
    }

    fun setView(@NonNull homeView: HomeView) {
        this.mHomeView = homeView
    }

    fun fetchPets() {
        mHomeView!!.showProgress()
        mGetPetListUseCase.execute(PetListObserver(), null)
    }

    fun deletePet(pet: Pet) {
        mDeletePetUseCase.execute(PetDeleteObserver(), pet.externalId)
    }

    private fun onObserverError(error: Throwable) {
        error.message?.let { mHomeView!!.showError(it) }
    }

    private inner class PetListObserver : DisposableObserver<List<Pet>>() {

        override fun onNext(pets: List<Pet>) {
            mHomeView!!.hideProgress()
            mHomeView!!.showPets(pets)
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            mHomeView!!.hideProgress()
            onObserverError(error)
            mHomeView!!.showPets()
        }
    }

    private inner class PetDeleteObserver : DisposableObserver<Response<Void>>() {

        override fun onNext(void: Response<Void>) {
            mHomeView!!.showPets()
        }

        override fun onComplete() {
        }

        override fun onError(error: Throwable) {
            onObserverError(error)
        }
    }
}