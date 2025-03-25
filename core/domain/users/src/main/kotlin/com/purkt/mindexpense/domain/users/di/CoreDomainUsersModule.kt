package com.purkt.mindexpense.domain.users.di

import com.purkt.mindexpense.data.users.di.coreDataUsersModule
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserUseCase
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreDomainUsersModule = module {
    includes(coreDataUsersModule)
    factoryOf(::GetCurrentUserUseCaseImpl) { bind<GetCurrentUserUseCase>() }
}