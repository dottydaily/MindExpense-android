package com.purkt.mindexpense.features.expense.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.android.BaseViewModel
import com.purkt.mindexpense.core.domain.expense.model.ValidateAmountResult
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseAmountUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseNoteUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseRecipientUseCase
import com.purkt.mindexpense.core.domain.expense.usecase.ValidateExpenseTitleUseCase
import com.purkt.mindexpense.core.domain.users.usecase.GetCurrentUserOrCreateNewOneUseCase
import com.purkt.mindexpense.core.ui.common.model.UiEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

internal abstract class BaseExpenseViewModel(
    private val validateExpenseTitleUseCase: ValidateExpenseTitleUseCase,
    private val validateExpenseRecipientUseCase: ValidateExpenseRecipientUseCase,
    private val validateExpenseAmountUseCase: ValidateExpenseAmountUseCase,
    private val validateExpenseNoteUseCase: ValidateExpenseNoteUseCase,
): BaseViewModel() {
    protected var initialTitle = ""
    protected var initialRecipient = ""
    protected var initialAmount = 0.0
    protected var initialDisplayAmount = ""
    protected var initialPaidAt = LocalDateTime.now()
    protected var initialNote = ""

    var title by mutableStateOf(initialTitle); protected set
    var isTitleError by mutableStateOf(false); private set
    var maxTitleLength: Int? by mutableStateOf(validateExpenseTitleUseCase.maxLength); private set
    var recipient by mutableStateOf(initialRecipient); protected set
    var isRecipientError by mutableStateOf(false); private set
    var maxRecipientLength: Int? by mutableStateOf(validateExpenseRecipientUseCase.maxLength); private set
    var amount by mutableDoubleStateOf(initialAmount); protected set
    var displayAmount by mutableStateOf(initialDisplayAmount); protected set
    var isAmountError by mutableStateOf(false); private set
    var paidAt: LocalDateTime by mutableStateOf(initialPaidAt); protected set
    var note by mutableStateOf(initialNote); protected set
    var isNoteError by mutableStateOf(false); private set
    var maxNoteLength: Int? by mutableStateOf(validateExpenseNoteUseCase.maxLength); private set
    var isShowingGoBackConfirmDialog by mutableStateOf(false); private set
    var isShowingSubmitSuccessDialog by mutableStateOf(false); private set
    var goBackUiEvent: UiEvent<Boolean> by mutableStateOf(UiEvent(false)); private set

    abstract fun submit()

    fun updateTitle(titleInput: String) {
        validateTitle(target = titleInput)
        title = titleInput
    }

    fun validateTitle(target: String = title) {
        isTitleError = validateExpenseTitleUseCase.execute(input = target) == false
    }

    fun updateRecipient(recipientInput: String) {
        validateRecipient(target = recipientInput)
        recipient = recipientInput
    }

    fun validateRecipient(target: String = recipient) {
        isRecipientError = validateExpenseRecipientUseCase.execute(input = target) == false
    }

    fun updateAmount(amountInput: String) {
        val result = validateExpenseAmountUseCase.execute(
            input = amountInput,
            fallBackDisplayAmount = displayAmount,
            fallbackValidatedAmount = amount,
        )
        updateAmountErrorStatus(result = result)
        displayAmount = result.displayAmount
        amount = result.validatedAmount
    }

    fun validateAmount(target: String = displayAmount) {
        updateAmountErrorStatus(
            result = validateExpenseAmountUseCase.execute(
                input = target,
                fallBackDisplayAmount = displayAmount,
                fallbackValidatedAmount = amount,
            )
        )
    }

    fun updateNote(noteInput: String) {
        validateNote(target = noteInput)
        note = noteInput
    }

    fun validateNote(target: String = note) {
        isNoteError = validateExpenseNoteUseCase.execute(input = target) == false
    }

    fun updatePaidAt(paidAtInput: LocalDateTime) {
        paidAt = paidAtInput
    }

    fun setGoBackConfirmDialog(isShowing: Boolean) {
        isShowingGoBackConfirmDialog = isShowing
    }

    fun setSubmitSuccessDialog(isShowing: Boolean) {
        isShowingSubmitSuccessDialog = isShowing
    }

    fun goBackOrShowConfirmDialog() {
        if (
            title != initialTitle ||
            recipient != initialRecipient ||
            amount != initialAmount ||
            paidAt != initialPaidAt ||
            note != initialNote
        ) {
            setGoBackConfirmDialog(isShowing = true)
        } else {
            goBackToPreviousPage()
        }
    }

    fun goBackToPreviousPage() {
        goBackUiEvent = UiEvent(true)
    }

    private fun updateAmountErrorStatus(result: ValidateAmountResult) {
        isAmountError = result.isError
    }
}