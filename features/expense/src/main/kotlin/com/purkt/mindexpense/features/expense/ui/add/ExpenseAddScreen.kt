package com.purkt.mindexpense.features.expense.ui.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.purkt.mindexpense.features.expense.ui.composable.BaseExpenseScreen
import com.purkt.mindexpense.features.expense.ui.composable.ExpenseScreenType
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
internal fun ExpenseAddScreen(
    onGoBackToPreviousPage: () -> Unit,
    viewModel: ExpenseAddViewModel = koinViewModel(),
) {
    val onUpdateTitle: (String) -> Unit = remember {
        { viewModel.updateTitle(titleInput = it) }
    }
    val onUpdateRecipient: (String) -> Unit = remember {
        { viewModel.updateRecipient(recipientInput = it) }
    }
    val onUpdateAmount: (String) -> Unit = remember {
        { viewModel.updateAmount(amountInput = it) }
    }
    val onUpdateNote: (String) -> Unit = remember {
        { viewModel.updateNote(noteInput = it)}
    }
    val onUpdatePaidAt: (LocalDateTime) -> Unit = remember {
        { viewModel.updatePaidAt(paidAtInput = it) }
    }

    val onSubmit: () -> Unit = remember {
        { viewModel.submit() }
    }
    BaseExpenseScreen(
        mode = ExpenseScreenType.ADD,
        title = viewModel.title,
        onTitleChanged = onUpdateTitle,
        isTitleError = viewModel.isTitleError,
        maxTitleLength = viewModel.maxTitleLength,
        recipient = viewModel.recipient,
        onRecipientChanged = onUpdateRecipient,
        isRecipientError = viewModel.isRecipientError,
        maxRecipientLength = viewModel.maxRecipientLength,
        amount = viewModel.amount,
        displayAmount = viewModel.displayAmount,
        onAmountChanged = onUpdateAmount,
        isAmountError = viewModel.isAmountError,
        paidAt = viewModel.paidAt,
        onPaidAtChanged = onUpdatePaidAt,
        note = viewModel.note,
        onNoteChanged = onUpdateNote,
        isNoteError = viewModel.isNoteError,
        maxNoteLength = viewModel.maxNoteLength,
        onClickSubmitButton = onSubmit,
        onGoBack = onGoBackToPreviousPage,
    )
}