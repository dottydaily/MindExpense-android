package com.purkt.mindexpense.home.di

import com.purkt.mindexpense.domain.expense.di.coreDomainExpenseModule
import com.purkt.mindexpense.domain.users.di.coreDomainUsersModule
import com.purkt.mindexpense.home.ui.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureHomeModule = module {
    includes(coreDomainExpenseModule, coreDomainUsersModule)
    viewModelOf(::HomeViewModel)
}