package com.purkt.mindexpense.features.expense.ui.add

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
import kotlinx.coroutines.launch

internal class ExpenseAddViewModel(
    getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
    validateExpenseTitleUseCase: ValidateExpenseTitleUseCase,
    validateExpenseRecipientUseCase: ValidateExpenseRecipientUseCase,
    validateExpenseAmountUseCase: ValidateExpenseAmountUseCase,
    validateExpenseNoteUseCase: ValidateExpenseNoteUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,
): BaseExpenseViewModel(
    getCurrentUserOrCreateNewOneUseCase = getCurrentUserOrCreateNewOneUseCase,
    validateExpenseTitleUseCase = validateExpenseTitleUseCase,
    validateExpenseRecipientUseCase = validateExpenseRecipientUseCase,
    validateExpenseAmountUseCase = validateExpenseAmountUseCase,
    validateExpenseNoteUseCase = validateExpenseNoteUseCase,
) {
    override fun submit() {
        validateTitle()
        validateRecipient()
        validateAmount()
        validateNote()

        if (isTitleError || isRecipientError || isAmountError || isNoteError) return

        viewModelScope.launch {
            try {
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
            } catch (e: Throwable) {
                AppLogger.e(e)
            }
        }
    }
}