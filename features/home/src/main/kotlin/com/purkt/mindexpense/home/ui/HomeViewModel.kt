package com.purkt.mindexpense.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.common.BaseViewModel
import com.purkt.mindexpense.data.expense.model.Expense
import com.purkt.mindexpense.data.expense.repository.ExpenseRepository
import com.purkt.mindexpense.domain.expense.usecase.CreateRandomExpenseUseCase
import com.purkt.mindexpense.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val expenseRepository: ExpenseRepository,
    private val createRandomExpenseUseCase: CreateRandomExpenseUseCase,
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
): BaseViewModel() {
    var currentUserId: Int? by mutableStateOf(null); private set

    val expenses: StateFlow<List<Expense>> =
        flow { emitAll(getExpensesByCurrentUserOrCreateNewUserIfNeeded()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )

    fun createRandomExpense() {
        viewModelScope.launch {
            currentUserId?.let { createRandomExpenseUseCase.execute(it) }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getExpensesByCurrentUserOrCreateNewUserIfNeeded(): Flow<List<Expense>> {
        return getCurrentUserOrCreateNewOneUseCase.execute()
            .flatMapLatest { user ->
                user?.let { currentUser ->
                    currentUserId = currentUser.localId

                    expenseRepository.getExpenses(
                        userId = if (currentUser.isOnline() && currentUser.remoteId != null) {
                            currentUser.remoteId!!
                        } else currentUser.localId
                    )
                } ?: emptyFlow()
            }
    }
}