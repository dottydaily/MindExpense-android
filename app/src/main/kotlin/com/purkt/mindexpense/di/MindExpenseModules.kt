package com.purkt.mindexpense.di

import com.purkt.mindexpense.core.database.di.coreDatabaseModule
import com.purkt.mindexpense.core.logging.MyLogger
import com.purkt.mindexpense.home.di.featureHomeModule
import com.purkt.mindexpense.logger.MyLoggerImpl
import com.purkt.mindexpress.profile.di.featureUsersModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val mindExpenseAppModules = module {
    includes(
        coreDatabaseModule,
        featureHomeModule,
        featureUsersModule,
    )

    singleOf(::MyLoggerImpl) { bind<MyLogger>() }
}