package com.cleganeBowl2k18.trebuchet.presenter.home

import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.domain.interactor.DeletePet
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetPetList
import com.cleganeBowl2k18.trebuchet.presentation.view.home.HomePresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.home.HomeView
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @Mock
    private lateinit var mockPet: Pet
    @Mock
    private lateinit var mockPetList: List<Pet>
    @Mock
    private lateinit var mockHomeView: HomeView
    @Mock
    private lateinit var mockGetPetList: GetPetList
    @Mock
    private lateinit var mockDeletePet: DeletePet

    private lateinit var mHomePresenter: HomePresenter

    @Before
    fun setup() {
        mHomePresenter = HomePresenter(mockGetPetList, mockDeletePet)
        mHomePresenter.setView(mockHomeView)
    }

    @Test
    fun getPetsForceRefresh_successCallsShowPets() {
        mHomePresenter.fetchPets()

        val inOrder = inOrder(mockHomeView)
        inOrder.verify(mockHomeView).showProgress()
    }
}