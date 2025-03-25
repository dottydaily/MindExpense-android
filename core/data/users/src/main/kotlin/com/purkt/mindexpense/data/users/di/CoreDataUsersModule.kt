package com.purkt.mindexpense.data.users.di

import com.purkt.mindexpense.data.users.database.FakeUserRepositoryImpl
import com.purkt.mindexpense.data.users.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataUsersModule = module {
    singleOf(::FakeUserRepositoryImpl) { bind<UserRepository>() }
}