package com.purkt.mindexpense.features.expense.ui.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.domain.expense.usecase.CreateExpenseUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseAmountUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseNoteUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseRecipientUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseTitleUseCase
import com.purkt.mindexpense.core.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import com.purkt.mindexpense.core.logging.AppLogger
import com.purkt.mindexpense.features.expense.ui.BaseExpenseViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

internal class ExpenseAddViewModel(
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
    validateExpenseTitleUseCase: ValidateExpenseTitleUseCase,
    validateExpenseRecipientUseCase: ValidateExpenseRecipientUseCase,
    validateExpenseAmountUseCase: ValidateExpenseAmountUseCase,
    validateExpenseNoteUseCase: ValidateExpenseNoteUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,
): BaseExpenseViewModel(
    validateExpenseTitleUseCase = validateExpenseTitleUseCase,
    validateExpenseRecipientUseCase = validateExpenseRecipientUseCase,
    validateExpenseAmountUseCase = validateExpenseAmountUseCase,
    validateExpenseNoteUseCase = validateExpenseNoteUseCase,
) {
    var isLoading by mutableStateOf(false); private set

    override fun submit() {
        try {
            isLoading = true

            validateTitle()
            validateRecipient()
            validateAmount()
            validateNote()

            if (isTitleError || isRecipientError || isAmountError || isNoteError) return

            viewModelScope.launch {
                val currentUserId = getCurrentUserOrCreateNewOneUseCase.execute()
                    .firstOrNull()
                    ?.currentId

                val isAdded = createExpenseUseCase.execute(
                    expense = Expense(
                        ownerUserId = currentUserId!!,
                        title = title,
                        recipient = recipient,
                        note = note,
                        amount = amount,
                        paidAt = paidAt,
                    ),
                )

                if (isAdded) {
                    setSubmitSuccessDialog(isShowing = true)
                }
            }
        } catch (e: Throwable) {
            AppLogger.e(e)
        } finally {
            isLoading = false
        }
    }
}