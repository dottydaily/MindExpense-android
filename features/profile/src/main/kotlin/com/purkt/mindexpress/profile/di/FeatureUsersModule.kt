package com.purkt.mindexpress.profile.di

import com.purkt.mindexpense.domain.users.di.coreDomainUsersModule
import com.purkt.mindexpress.profile.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureUsersModule = module {
    includes(coreDomainUsersModule)
    viewModelOf(::ProfileViewModel)
}