package com.purkt.mindexpense.features.expense.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.domain.expense.usecase.GetExpenseByIdUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.UpdateExpenseUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseAmountUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseNoteUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseRecipientUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseTitleUseCase
import com.purkt.mindexpense.core.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.features.expense.ui.BaseExpenseViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime

internal class ExpenseEditViewModel(
    expenseId: String,
    hasBeenSynced: Boolean,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
    validateExpenseTitleUseCase: ValidateExpenseTitleUseCase,
    validateExpenseRecipientUseCase: ValidateExpenseRecipientUseCase,
    validateExpenseAmountUseCase: ValidateExpenseAmountUseCase,
    validateExpenseNoteUseCase: ValidateExpenseNoteUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
): BaseExpenseViewModel(
    validateExpenseTitleUseCase = validateExpenseTitleUseCase,
    validateExpenseRecipientUseCase = validateExpenseRecipientUseCase,
    validateExpenseAmountUseCase = validateExpenseAmountUseCase,
    validateExpenseNoteUseCase = validateExpenseNoteUseCase,
) {
    private var currentUserId: Int? = null
    private var currentExpense: Expense? = null
    var isLoading by mutableStateOf(false); private set

    init {
        fetchExpenseId(expenseId = expenseId, isRemoteId = hasBeenSynced)
    }

    override fun submit() {
        viewModelScope.launch {
            try {
                isLoading = true

                validateTitle()
                validateRecipient()
                validateAmount()
                validateNote()

                if (isTitleError || isRecipientError || isAmountError || isNoteError) return@launch

                val isUpdated = updateExpenseUseCase.execute(
                    expense = currentExpense!!.copy(
                        ownerUserId = currentUserId!!,
                        title = title,
                        recipient = recipient,
                        note = note,
                        amount = amount,
                        paidAt = paidAt,
                    ),
                )

                if (isUpdated) {
                    setSubmitSuccessDialog(isShowing = true)
                } else throw Exception("Failed to update expense")

                isLoading = false

            } catch (e: Throwable) {
                AppLogger.e(e)
                isLoading = false
            }
        }
    }

    private fun fetchExpenseId(expenseId: String, isRemoteId: Boolean) {
        viewModelScope.launch {
            try {
                isLoading = true

                currentUserId = getCurrentUserOrCreateNewOneUseCase.execute()
                    .firstOrNull()
                    ?.currentId

                currentExpense = getExpenseByIdUseCase.execute(
                    userId = currentUserId!!,
                    expenseId = expenseId,
                    isRemoteId = isRemoteId,
                )

                if (currentExpense != null) {
                    title = (currentExpense?.title.orEmpty()).also { initialTitle = it }
                    recipient = (currentExpense?.recipient.orEmpty()).also { initialRecipient = it }
                    amount = (currentExpense?.amount ?: 0.0).also { initialAmount = it }
                    displayAmount = (amount.formatCurrencyOrNull().orEmpty()).also { initialDisplayAmount = it }
                    paidAt = (currentExpense?.paidAt ?: LocalDateTime.now()).also { initialPaidAt = it }
                    note = (currentExpense?.note ?: "").also { initialNote = it }
                } else throw NullPointerException("target expense is null")
            } catch (e: Throwable) {
                AppLogger.e(e)
            } finally {
                isLoading = false
            }
        }
    }
}