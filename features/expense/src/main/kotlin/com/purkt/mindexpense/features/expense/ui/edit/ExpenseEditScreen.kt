package com.purkt.mindexpense.features.expense.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.purkt.mindexpense.features.expense.ui.composable.BaseExpenseScreen
import com.purkt.mindexpense.features.expense.ui.composable.ExpenseScreenType

@Composable
internal fun ExpenseEditScreen(
    onGoBackToPreviousPage: () -> Unit,
    viewModel: ExpenseEditViewModel,
) {
    LaunchedEffect(key1 = viewModel.goBackUiEvent) {
        if (viewModel.goBackUiEvent.consume() == true) {
            onGoBackToPreviousPage.invoke()
        }
    }

    BaseExpenseScreen(
        mode = ExpenseScreenType.EDIT,
        isLoading = viewModel.isLoading,
        title = viewModel.title,
        onTitleChanged = viewModel::updateTitle,
        onValidateTitle = viewModel::validateTitle,
        isTitleError = viewModel.isTitleError,
        maxTitleLength = viewModel.maxTitleLength,
        recipient = viewModel.recipient,
        onRecipientChanged = viewModel::updateRecipient,
        onValidateRecipient = viewModel::validateRecipient,
        isRecipientError = viewModel.isRecipientError,
        maxRecipientLength = viewModel.maxRecipientLength,
        amount = viewModel.amount,
        displayAmount = viewModel.displayAmount,
        onAmountChanged = viewModel::updateAmount,
        onValidateAmount = viewModel::validateAmount,
        isAmountError = viewModel.isAmountError,
        paidAt = viewModel.paidAt,
        onPaidAtChanged = viewModel::updatePaidAt,
        note = viewModel.note,
        onNoteChanged = viewModel::updateNote,
        onValidateNote = viewModel::validateNote,
        isNoteError = viewModel.isNoteError,
        maxNoteLength = viewModel.maxNoteLength,
        onClickSubmitButton = viewModel::submit,
        onGoBackOrOpenConfirmDialog = viewModel::goBackOrShowConfirmDialog,
        isShowingGoBackConfirmDialog = viewModel.isShowingGoBackConfirmDialog,
        onDismissGoBackConfirmDialog = { viewModel.setGoBackConfirmDialog(isShowing = false) },
        isShowingSubmitSuccessDialog = viewModel.isShowingSubmitSuccessDialog,
        onDismissSubmitSuccessDialog = { viewModel.setSubmitSuccessDialog(isShowing = false) },
        onGoBack = viewModel::goBackToPreviousPage,
    )
}