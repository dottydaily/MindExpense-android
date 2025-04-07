package com.purkt.mindexpense.features.expense.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.purkt.mindexpense.core.android.BaseViewModel
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
    private val getCurrentUserOrCreateNewOneUseCase: GetCurrentUserOrCreateNewOneUseCase,
    private val validateExpenseTitleUseCase: ValidateExpenseTitleUseCase,
    private val validateExpenseRecipientUseCase: ValidateExpenseRecipientUseCase,
    private val validateExpenseAmountUseCase: ValidateExpenseAmountUseCase,
    private val validateExpenseNoteUseCase: ValidateExpenseNoteUseCase,
): BaseViewModel() {
    protected var currentUserId: Int? = null

    var title by mutableStateOf(""); private set
    var isTitleError by mutableStateOf(false); private set
    var maxTitleLength: Int? by mutableStateOf(validateExpenseTitleUseCase.maxLength); private set
    var recipient by mutableStateOf(""); private set
    var isRecipientError by mutableStateOf(false); private set
    var maxRecipientLength: Int? by mutableStateOf(validateExpenseRecipientUseCase.maxLength); private set
    var amount by mutableDoubleStateOf(0.0); private set
    var displayAmount by mutableStateOf(""); private set
    var isAmountError by mutableStateOf(false); private set
    var note by mutableStateOf(""); private set
    var isNoteError by mutableStateOf(false); private set
    var maxNoteLength: Int? by mutableStateOf(validateExpenseNoteUseCase.maxLength); private set
    var paidAt: LocalDateTime by mutableStateOf(LocalDateTime.now()); private set
    var goBackUiEvent: UiEvent<Boolean> by mutableStateOf(UiEvent(false)); private set

    init {
        viewModelScope.launch {
            currentUserId = withContext(coroutineContext) {
                getCurrentUserOrCreateNewOneUseCase.execute().firstOrNull()?.currentId
            }
        }
    }

    abstract fun submit()

    fun updateTitle(titleInput: String) {
        isTitleError = validateExpenseTitleUseCase.execute(input = titleInput)
        title = titleInput
    }

    fun updateRecipient(recipientInput: String) {
        isRecipientError = validateExpenseRecipientUseCase.execute(input = recipientInput)
        recipient = recipientInput
    }

    fun updateAmount(amountInput: String) {
        val result = validateExpenseAmountUseCase.execute(
            input = amountInput,
            fallBackDisplayAmount = displayAmount,
            fallbackValidatedAmount = amount,
        )
        displayAmount = result.displayAmount
        amount = result.validatedAmount
        isAmountError = result.isError
    }

    fun updateNote(noteInput: String) {
        isNoteError = validateExpenseNoteUseCase.execute(input = noteInput)
        note = noteInput
    }

    fun updatePaidAt(paidAtInput: LocalDateTime) {
        paidAt = paidAtInput
    }

    fun triggerGoBackUiEvent() {
        goBackUiEvent = UiEvent(true)
    }
}