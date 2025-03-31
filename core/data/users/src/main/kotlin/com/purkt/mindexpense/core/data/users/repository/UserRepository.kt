package com.purkt.mindexpense.core.data.users.repository

import com.purkt.mindexpense.core.data.users.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User, forceActive: Boolean = false): Boolean
    fun getUser(userId: Int): Flow<User?>
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(user: User): Boolean
}