package com.purkt.mindexpense.features.profile.di

import com.purkt.mindexpense.core.domain.users.di.coreDomainUsersModule
import com.purkt.mindexpense.features.profile.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureUsersModule = module {
    includes(coreDomainUsersModule)
    viewModelOf(::ProfileViewModel)
}