package com.purkt.mindexpense.core.database.di

import androidx.room.Room
import com.purkt.mindexpense.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val databaseNameQualifier = "DATABASE_NAME"

val coreDatabaseModule = module {
    includes(databaseInfoModule)
    includes(daoModule)

    single<AppDatabase> {
        val name = get<String>(qualifier = named(databaseNameQualifier))
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = name,
        ).build()
    }
}