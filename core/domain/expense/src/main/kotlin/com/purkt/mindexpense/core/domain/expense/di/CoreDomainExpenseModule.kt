package com.purkt.mindexpense.core.domain.expense.di

import com.purkt.mindexpense.core.data.expense.di.coreDataExpenseModule
import com.purkt.mindexpense.core.domain.expense.usecase.CreateRandomExpenseUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.CreateRandomExpenseUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreDomainExpenseModule = module {
    includes(coreDataExpenseModule)
    factoryOf(::CreateRandomExpenseUseCaseImpl) { bind<CreateRandomExpenseUseCase>() }
}