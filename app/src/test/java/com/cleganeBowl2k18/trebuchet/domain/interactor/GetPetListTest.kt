package com.cleganeBowl2k18.trebuchet.domain.interactor

import com.cleganeBowl2k18.trebuchet.data.network.PetStoreService
import com.cleganeBowl2k18.trebuchet.data.repository.PetRepository
import com.cleganeBowl2k18.trebuchet.domain.excecutor.PostExecutionThread
import com.cleganeBowl2k18.trebuchet.domain.excecutor.ThreadExecutor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPetListTest {

    @Mock
    private lateinit var mockThreadExecutor: ThreadExecutor
    @Mock
    private lateinit var mockPostExecutionThread: PostExecutionThread
    @Mock
    private lateinit var mockPetService: PetStoreService
    @Mock
    private lateinit var mockPetRepository: PetRepository
    
    private lateinit var getPetList: GetPetList

    @Before
    fun setUp() {
        getPetList = GetPetList(mockPetRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun getPets_forwardsRequestToRepository() {
        getPetList.buildUseCaseObservable(null)

        verify<PetRepository>(mockPetRepository).petsAvailable()
        verifyNoMoreInteractions(mockPetRepository)
        verifyZeroInteractions(mockThreadExecutor)
        verifyZeroInteractions(mockPostExecutionThread)
    }
}