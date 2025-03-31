package com.purkt.mindexpense.core.data.users.database

import com.purkt.mindexpense.core.data.common.suspendTryOrDefault
import com.purkt.mindexpense.core.data.common.toIsoDateTimeStringOrThrowError
import com.purkt.mindexpense.core.data.common.toLocalDateTimeOrThrowError
import com.purkt.mindexpense.core.data.common.tryOrDefault
import com.purkt.mindexpense.core.data.users.database.dao.UsersDao
import com.purkt.mindexpense.core.data.users.database.entity.UserEntity
import com.purkt.mindexpense.core.data.users.model.User
import com.purkt.mindexpense.core.data.users.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext

internal class LocalUserRepositoryImpl(private val dao: UsersDao): UserRepository {
    override suspend fun createUser(user: User, forceActive: Boolean): Boolean {
        return tryOrDefault(default = false) {
            if (user.localId != User.DEFAULT_LOCAL_ID) {
                throw IllegalArgumentException("User should have default local id when creating")
            }

            val entity = user.mapToEntityOrThrowError(forceActive = forceActive)
            val resultId = dao.createUser(entity)
            resultId > 0
        }
    }

    override fun getUser(userId: Int): Flow<User?> {
        return tryOrDefault(default = flow { emit(null) }) {
            dao.getUserById(userId = userId)
                .transform { emit(it?.mapToModelOrThrowError()) }
                .flowOn(Dispatchers.IO)
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return tryOrDefault(default = flow { emit(null) }) {
            dao.getCurrentUser()
                .transform { emit(it?.mapToModelOrThrowError()) }
                .flowOn(Dispatchers.IO)
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        return suspendTryOrDefault(default = false) {
            val entity = user.mapToEntityOrThrowError()
            val affectedRow = withContext(Dispatchers.IO) { dao.updateUser(user = entity) }
            affectedRow > 0
        }
    }

    override suspend fun deleteUser(user: User): Boolean {
        return suspendTryOrDefault(default = false) {
            val entity = user.mapToEntityOrThrowError()
            val affectedRow = withContext(Dispatchers.IO) { dao.deleteUser(user = entity) }
            affectedRow > 0
        }
    }
}

private fun User.mapToEntityOrThrowError(forceActive: Boolean = false): UserEntity {
    return UserEntity(
        localId = localId,
        remoteId = remoteId,
        email = email,
        displayName = displayName,
        profileUrl = profileUrl,
        createdAtIsoDateTime = createdAt.toIsoDateTimeStringOrThrowError(),
        updatedAtIsoDateTime = updatedAt.toIsoDateTimeStringOrThrowError(),
        isUsing = if (forceActive) true else isUsing,
    )
}

private fun UserEntity.mapToModelOrThrowError(): User {
    return User(
        localId = localId,
        remoteId = remoteId,
        email = email.orEmpty(),
        displayName = displayName.orEmpty(),
        profileUrl = profileUrl.orEmpty(),
        createdAt = createdAtIsoDateTime.toLocalDateTimeOrThrowError(),
        updatedAt = updatedAtIsoDateTime.toLocalDateTimeOrThrowError(),
        isUsing = isUsing,
    )
}