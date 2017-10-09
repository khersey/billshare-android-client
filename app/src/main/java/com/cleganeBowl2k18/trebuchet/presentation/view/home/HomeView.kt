package com.cleganeBowl2k18.trebuchet.presentation.view.home

import com.cleganeBowl2k18.trebuchet.data.entity.Pet
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

interface HomeView : ProgressView {

    fun showPets(pets: List<Pet>)

    fun showPet(pet: Pet)

    fun showEditPet(pet: Pet)

    fun showPets()

    fun showError(message: String)
}