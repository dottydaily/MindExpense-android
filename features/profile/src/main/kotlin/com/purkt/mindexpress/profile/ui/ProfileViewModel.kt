package com.purkt.mindexpress.profile.ui

import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.common.BaseViewModel
import com.purkt.mindexpense.data.users.model.User
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

internal class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
): BaseViewModel() {
    val currentUser: StateFlow<User?> = getCurrentUserUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
}