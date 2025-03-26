package com.purkt.mindexpress.profile.ui

import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.common.BaseViewModel
import com.purkt.mindexpense.data.users.model.User
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

internal class ProfileViewModel(
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
): BaseViewModel() {
    val currentUser: StateFlow<User?> = flow { emitAll(getCurrentUserOrCreateNewOneUseCase.execute()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
}