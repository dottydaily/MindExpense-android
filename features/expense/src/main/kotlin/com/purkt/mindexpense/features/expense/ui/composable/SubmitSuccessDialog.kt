package com.purkt.mindexpense.features.expense.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpenseDialog
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
internal fun SubmitSuccessDialog(
    isShowing: Boolean,
    onDismissDialog: () -> Unit = {},
    onClickConfirmButton: () -> Unit = {},
) {
    if (isShowing) {
        MindExpenseDialog(
            title = stringResource(id = R.string.expense_submit_success_dialog_title),
            onClickDismiss = onDismissDialog,
            onClickConfirm = onClickConfirmButton,
            shouldShowDismissButton = false,
        )
    }
}

@MindExpensePreview
@Composable
private fun SubmitSuccessDialogPreview() {
    MindExpenseTheme {
        SubmitSuccessDialog(
            isShowing = true,
            onDismissDialog = {},
            onClickConfirmButton = {},
        )
    }
}