package com.cleganeBowl2k18.trebuchet.presentation.internal.di.module

import com.cleganeBowl2k18.trebuchet.data.network.PetStoreService
import com.cleganeBowl2k18.trebuchet.data.repository.PetRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun providePetRepository(service: PetStoreService): PetRepository {
        return PetRepository(service)
    }
}
