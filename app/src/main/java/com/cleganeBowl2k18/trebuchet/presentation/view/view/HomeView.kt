package com.cleganeBowl2k18.trebuchet.presentation.view.view

import com.cleganeBowl2k18.trebuchet.data.models.Pet
import com.cleganeBowl2k18.trebuchet.presentation.common.view.ProgressView

interface HomeView : ProgressView {

    fun showPets(pets: List<Pet>)

    fun showPet(pet: Pet)

    fun showEditPet(pet: Pet)

    fun showPets()

    fun showError(message: String)
}
