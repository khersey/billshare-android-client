package com.cleganeBowl2k18.trebuchet.data.models

class EntityMocker {

    companion object Factory {

        fun getExternalId(): Long = 123456789

        fun getMockerPet(): Pet {
            val pet = Pet()
            pet.externalId = getExternalId()
            pet.name = "Spike"
            pet.status = "available"
            return pet
        }
    }
}