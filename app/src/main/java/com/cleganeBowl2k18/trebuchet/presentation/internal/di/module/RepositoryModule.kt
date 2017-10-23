package com.cleganeBowl2k18.trebuchet.presentation.internal.di.module

import com.cleganeBowl2k18.trebuchet.data.network.*
import com.cleganeBowl2k18.trebuchet.data.repository.GroupRepository
import com.cleganeBowl2k18.trebuchet.data.repository.PetRepository
import com.cleganeBowl2k18.trebuchet.data.repository.TransactionRepository
import com.cleganeBowl2k18.trebuchet.data.repository.UserRepository
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

    @Provides
    @Singleton
    internal fun provideGroupRepository(service: GroupService): GroupRepository {
        return GroupRepository(service)
    }

    @Provides
    @Singleton
    internal fun providesUserRepository(service: UserService) : UserRepository {
        return UserRepository(service)
    }

    @Provides
    @Singleton
    internal fun providesTransactionRepository(service: TransactionService) : TransactionRepository {
        return TransactionRepository(service)
    }
}
