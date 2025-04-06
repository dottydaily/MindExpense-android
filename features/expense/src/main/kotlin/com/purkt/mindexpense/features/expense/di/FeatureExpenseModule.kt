package com.purkt.mindexpense.features.expense.di

import com.purkt.mindexpense.features.expense.ui.add.ExpenseAddViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureExpenseModule = module {
    viewModelOf(::ExpenseAddViewModel)
}