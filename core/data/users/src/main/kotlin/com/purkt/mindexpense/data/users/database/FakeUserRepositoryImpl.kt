package com.purkt.mindexpense.data.users.database

import com.purkt.mindexpense.data.users.model.User
import com.purkt.mindexpense.data.users.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class FakeUserRepositoryImpl: UserRepository {
    override suspend fun createUser(user: User): Boolean {
        return true
    }

    override fun getUser(userId: String): Flow<User?> {
        return flow {
            val createdAt = LocalDateTime.parse(
                "2024-12-25T00:00:00Z",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            )
            val updatedAt = LocalDateTime.parse(
                "2025-02-25T00:00:00Z",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            )
            val target = User(
                localId = "1",
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            val another = target.copy(localId = "2")

            while (true) {
                emit(target)
                delay(5000)
                emit(another)
                delay(5000)
                emit(null)
                delay(5000)
            }
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        val id = "1"
        return getUser(id)
    }

    override suspend fun updateUser(user: User): Boolean {
        return true
    }

    override suspend fun deleteUser(user: User): Boolean {
        return true
    }
}