package com.purkt.mindexpense.di

import com.purkt.mindexpense.home.di.featureHomeModule
import com.purkt.mindexpress.profile.di.featureUsersModule
import org.koin.dsl.module

internal val mindExpenseAppModules = module {
    includes(
        featureHomeModule,
        featureUsersModule,
    )
}