package com.purkt.mindexpense.core.database.di

import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val databaseInfoModule = module {
    factory<String>(qualifier = named(databaseNameQualifier)) { "mind_expense_dev" }
}