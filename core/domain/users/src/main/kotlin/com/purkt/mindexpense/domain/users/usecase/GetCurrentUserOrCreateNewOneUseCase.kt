package com.purkt.mindexpense.domain.users.usecase

import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.data.users.model.User
import com.purkt.mindexpense.data.users.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface GetCurrentUserOrCreateNewOneUseCase {
    suspend fun execute(): Flow<User?>
}

internal class GetCurrentUserOrCreateNewOneUseCaseImpl(
    private val repository: UserRepository,
): GetCurrentUserOrCreateNewOneUseCase {
    override suspend fun execute(): Flow<User?> {
        val currentUser = repository.getCurrentUser().firstOrNull()
        if (currentUser == null) {
            AppLogger.d("No current user found, creating new one...")
            repository.createUser(user = User(), forceActive = true)
        }
        return repository.getCurrentUser()
    }
}