package com.purkt.mindexpense.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.purkt.mindexpense.core.data.expense.database.dao.ExpenseDao
import com.purkt.mindexpense.core.data.expense.database.entity.ExpenseEntity
import com.purkt.mindexpense.core.data.users.database.dao.UsersDao
import com.purkt.mindexpense.core.data.users.database.entity.UserEntity
import com.purkt.mindexpense.core.database.converter.LocalDateTimeConverter

@Database(
    entities = [
        ExpenseEntity::class,
        UserEntity::class,
    ],
    version = 1,
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun usersDao(): UsersDao
}