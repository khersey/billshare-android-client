package com.cleganeBowl2k18.trebuchet.data.repository

import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.data.network.PetStoreService
import io.reactivex.Observable
import retrofit2.Response

class PetRepository(private val petStoreService: PetStoreService) {

    fun petsAvailable(): Observable<List<Pet>> = petStoreService.petsAvailable

    fun createPet(pet: Pet): Observable<Pet> = petStoreService.createPet(pet)

    fun updatePet(pet: Pet): Observable<Pet> = petStoreService.updatePet(pet)

    fun deletePet(petId: Long): Observable<Response<Void>> = petStoreService.deletePet(petId)

    fun getPet(petId: Long): Observable<Pet> = petStoreService.getPet(petId)
}