package com.purkt.mindexpense.core.database.di

import com.purkt.mindexpense.core.database.AppDatabase
import com.purkt.mindexpense.data.expense.database.dao.ExpenseDao
import com.purkt.mindexpense.data.users.database.dao.UsersDao
import org.koin.dsl.module

internal val daoModule = module {
    single<ExpenseDao> { get<AppDatabase>().expenseDao() }
    single<UsersDao> { get<AppDatabase>().usersDao() }
}