package com.purkt.mindexpense.domain.users.usecase

import com.purkt.mindexpense.data.users.model.User
import com.purkt.mindexpense.data.users.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface GetCurrentUserUseCase {
    fun execute(): Flow<User?>
}

internal class GetCurrentUserUseCaseImpl(
    private val repository: UserRepository,
): GetCurrentUserUseCase {
    override fun execute(): Flow<User?> {
        return repository.getCurrentUser()
    }
}