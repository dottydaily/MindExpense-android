package com.purkt.mindexpense.features.home.di

import com.purkt.mindexpense.core.domain.expense.di.coreDomainExpenseModule
import com.purkt.mindexpense.core.domain.users.di.coreDomainUsersModule
import com.purkt.mindexpense.features.home.ui.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureHomeModule = module {
    includes(coreDomainExpenseModule, coreDomainUsersModule)
    viewModelOf(::HomeViewModel)
}