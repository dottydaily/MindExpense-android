package com.purkt.mindexpense.core.data.users.di

import com.purkt.mindexpense.core.data.users.database.LocalUserRepositoryImpl
import com.purkt.mindexpense.core.data.users.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataUsersModule = module {
    singleOf(::LocalUserRepositoryImpl) { bind<UserRepository>() }
}