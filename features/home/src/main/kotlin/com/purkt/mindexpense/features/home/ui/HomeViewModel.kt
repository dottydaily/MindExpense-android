package com.purkt.mindexpense.features.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.android.BaseViewModel
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.data.expense.repository.ExpenseRepository
import com.purkt.mindexpense.core.data.users.model.User
import com.purkt.mindexpense.core.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import com.purkt.mindexpense.core.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.time.YearMonth

internal class HomeViewModel(
    private val expenseRepository: ExpenseRepository,
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
): BaseViewModel() {
    private var collectExpensesJob: Job? = null
    private var targetYearMonth: YearMonth = YearMonth.now()
    private var currentUser: User? by mutableStateOf(null)

    var isLoading by mutableStateOf(false); private set
    var expenseToDeleted: Expense? by mutableStateOf(null); private set

    private val _expenses: MutableStateFlow<ExpensesByYearMonth?> = MutableStateFlow(null)
    val expensesByYearMonth: StateFlow<ExpensesByYearMonth?> = _expenses

    init {
        try {
            isLoading = true

            viewModelScope.launch {
                getOrCreateCurrentUser()!!.let {
                    currentUser = it
                    collectExpenses(
                        currentUserId = it.currentId,
                        yearMonth = targetYearMonth,
                    )
                }
            }
        } catch (e: Throwable) {
            AppLogger.e(e)
            setErrorState()
        }
    }

    fun setConfirmDeleteDialog(pendingExpense: Expense?) {
        expenseToDeleted = pendingExpense
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }

    fun changeToPreviousMonth() {
        try {
            targetYearMonth = targetYearMonth.minusMonths(1)
            collectExpenses(
                currentUserId = currentUser!!.currentId,
                yearMonth = targetYearMonth,
            )
        } catch (e: Throwable) {
            AppLogger.e(e)
            setErrorState()
        }
    }

    fun changeToNextMonth() {
        try {
            targetYearMonth = targetYearMonth.plusMonths(1)
            collectExpenses(
                currentUserId = currentUser!!.currentId,
                yearMonth = targetYearMonth,
            )
        } catch (e: Throwable) {
            AppLogger.e(e)
            setErrorState()
        }
    }

    private suspend fun getOrCreateCurrentUser(): User? {
        return getCurrentUserOrCreateNewOneUseCase.execute().firstOrNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectExpenses(
        currentUserId: Int,
        yearMonth: YearMonth,
    ) {
        collectExpensesJob?.cancel()
        collectExpensesJob = viewModelScope.launch(Dispatchers.IO) {
            // Show loading if it took time too long.
            val loadingJob = viewModelScope.launch {
                delay(400L)
                isLoading = true
            }

            expenseRepository.getExpenses(userId = currentUserId, specificYearMonth = yearMonth)
                .catch { setErrorState() }
                .mapLatest { ExpensesByYearMonth(expenses = it, yearMonth = yearMonth) }
                .collectLatest {
                    _expenses.value = it

                    if (loadingJob.isActive) {
                        loadingJob.cancel()
                    }

                    isLoading = false
                }
        }
    }

    private fun setErrorState() {
        _expenses.value = null
        isLoading = false
    }
}