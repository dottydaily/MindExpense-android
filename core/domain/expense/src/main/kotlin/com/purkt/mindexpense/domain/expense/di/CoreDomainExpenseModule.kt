package com.purkt.mindexpense.domain.expense.di

import com.purkt.mindexpense.data.expense.di.coreDataExpenseModule
import com.purkt.mindexpense.domain.expense.usecase.GetExpensesByUserIdUseCase
import com.purkt.mindexpense.domain.expense.usecase.GetExpensesByUserIdUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreDomainExpenseModule = module {
    includes(coreDataExpenseModule)
    factoryOf(::GetExpensesByUserIdUseCaseImpl) { bind<GetExpensesByUserIdUseCase>() }
}