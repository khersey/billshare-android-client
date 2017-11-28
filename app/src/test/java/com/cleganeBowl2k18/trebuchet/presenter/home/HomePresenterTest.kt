package com.cleganeBowl2k18.trebuchet.presenter.home

import com.cleganeBowl2k18.trebuchet.data.models.Pet
import com.cleganeBowl2k18.trebuchet.domain.interactor.DeletePet
import com.cleganeBowl2k18.trebuchet.domain.interactor.GetPetList
import com.cleganeBowl2k18.trebuchet.presentation.view.presenter.HomePresenter
import com.cleganeBowl2k18.trebuchet.presentation.view.view.HomeView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
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