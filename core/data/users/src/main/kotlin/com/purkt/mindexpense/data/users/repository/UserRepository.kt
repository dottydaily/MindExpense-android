package com.purkt.mindexpense.data.users.repository

import com.purkt.mindexpense.data.users.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): Boolean
    fun getUser(userId: String): Flow<User?>
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(user: User): Boolean
}