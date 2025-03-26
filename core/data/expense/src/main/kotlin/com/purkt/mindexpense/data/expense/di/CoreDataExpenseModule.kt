package com.purkt.mindexpense.data.expense.di

import com.purkt.mindexpense.data.expense.database.LocalExpenseRepositoryImpl
import com.purkt.mindexpense.data.expense.repository.ExpenseRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataExpenseModule = module {
    singleOf(::LocalExpenseRepositoryImpl) { bind<ExpenseRepository>() }
}