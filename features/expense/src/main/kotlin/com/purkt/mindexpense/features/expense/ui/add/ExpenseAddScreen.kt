package com.purkt.mindexpense.features.expense.ui.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.purkt.mindexpense.core.ui.common.composable.rememberListener
import com.purkt.mindexpense.core.ui.common.composable.rememberListenerParams
import com.purkt.mindexpense.features.expense.ui.composable.BaseExpenseScreen
import com.purkt.mindexpense.features.expense.ui.composable.ExpenseScreenType
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
internal fun ExpenseAddScreen(
    onGoBackToPreviousPage: () -> Unit,
    viewModel: ExpenseAddViewModel = koinViewModel(),
) {
    val onUpdateTitle: (String) -> Unit = rememberListenerParams { viewModel.updateTitle(it) }
    val onUpdateRecipient: (String) -> Unit = rememberListenerParams { viewModel.updateRecipient(it) }
    val onUpdateAmount: (String) -> Unit = rememberListenerParams { viewModel.updateAmount(it) }
    val onUpdateNote: (String) -> Unit = rememberListenerParams { viewModel.updateNote(it) }
    val onValidateTitle: (String) -> Unit = rememberListenerParams { viewModel.validateTitle() }
    val onValidateRecipient: (String) -> Unit = rememberListenerParams { viewModel.validateRecipient() }
    val onValidateAmount: (String) -> Unit = rememberListenerParams { viewModel.validateAmount() }
    val onValidateNote: (String) -> Unit = rememberListenerParams { viewModel.validateNote() }
    val onUpdatePaidAt: (LocalDateTime) -> Unit = rememberListenerParams { viewModel.updatePaidAt(it) }
    val onSubmit: () -> Unit = rememberListener { viewModel.submit() }
    val onGoBackOrOpenConfirmDialog: () -> Unit = rememberListener { viewModel.goBackOrShowConfirmDialog() }
    val onDismissGoBackConfirmDialog: () -> Unit = rememberListener { viewModel.setGoBackConfirmDialog(false) }
    val onDismissSubmitSuccessDialog: () -> Unit = rememberListener { viewModel.setSubmitSuccessDialog(false) }
    val onGoBack: () -> Unit = rememberListener { viewModel.goBackToPreviousPage() }

    LaunchedEffect(key1 = viewModel.goBackUiEvent) {
        if (viewModel.goBackUiEvent.consume() == true) {
            onGoBackToPreviousPage.invoke()
        }
    }

    BaseExpenseScreen(
        mode = ExpenseScreenType.ADD,
        title = viewModel.title,
        onTitleChanged = onUpdateTitle,
        onValidateTitle = onValidateTitle,
        isTitleError = viewModel.isTitleError,
        maxTitleLength = viewModel.maxTitleLength,
        recipient = viewModel.recipient,
        onRecipientChanged = onUpdateRecipient,
        onValidateRecipient = onValidateRecipient,
        isRecipientError = viewModel.isRecipientError,
        maxRecipientLength = viewModel.maxRecipientLength,
        amount = viewModel.amount,
        displayAmount = viewModel.displayAmount,
        onAmountChanged = onUpdateAmount,
        onValidateAmount = onValidateAmount,
        isAmountError = viewModel.isAmountError,
        paidAt = viewModel.paidAt,
        onPaidAtChanged = onUpdatePaidAt,
        note = viewModel.note,
        onNoteChanged = onUpdateNote,
        onValidateNote = onValidateNote,
        isNoteError = viewModel.isNoteError,
        maxNoteLength = viewModel.maxNoteLength,
        onClickSubmitButton = onSubmit,
        onGoBackOrOpenConfirmDialog = onGoBackOrOpenConfirmDialog,
        isShowingGoBackConfirmDialog = viewModel.isShowingGoBackConfirmDialog,
        onDismissGoBackConfirmDialog = onDismissGoBackConfirmDialog,
        isShowingSubmitSuccessDialog = viewModel.isShowingSubmitSuccessDialog,
        onDismissSubmitSuccessDialog = onDismissSubmitSuccessDialog,
        onGoBack = onGoBack,
    )
}