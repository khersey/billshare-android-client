package com.cleganeBowl2k18.trebuchet.data.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class PetTest {

    private lateinit var pet: Pet

    @Before
    fun setup() {
        pet = EntityMocker.getMockerPet()
    }

    @Test
    fun testPetConstructorHappyCase(){
        val userId = pet.externalId

        assertThat(userId).isEqualTo(EntityMocker.getExternalId())
    }
}