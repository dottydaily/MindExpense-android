package com.purkt.mindexpense.home.ui

import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.common.BaseViewModel
import com.purkt.mindexpense.data.expense.model.Expense
import com.purkt.mindexpense.domain.expense.usecase.GetExpensesByUserIdUseCase
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

internal class HomeViewModel(
    private val getExpensesByUserIdUseCase: GetExpensesByUserIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
): BaseViewModel() {

    val expenses: StateFlow<List<Expense>> = getExpensesByCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getExpensesByCurrentUser(): Flow<List<Expense>> {
        return getCurrentUserUseCase.execute()
            .flatMapLatest { user ->
                user?.let {
                    getExpensesByUserIdUseCase.execute(user.localId)
                } ?: emptyFlow()
            }
    }
}