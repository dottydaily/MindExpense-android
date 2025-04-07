package com.purkt.mindexpense.features.expense.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpenseDialog
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
internal fun ConfirmGoBackDialog(
    isShowing: Boolean,
    onDismissDialog: () -> Unit = {},
    onClickConfirmButton: () -> Unit = {},
) {
    if (isShowing) {
        MindExpenseDialog(
            title = stringResource(id = R.string.expense_go_back_dialog_title),
            onClickDismiss = onDismissDialog,
            onClickConfirm = onClickConfirmButton,
            dismissButtonText = stringResource(R.string.common_cancel_label),
        )
    }
}

@MindExpensePreview
@Composable
private fun ConfirmGoBackDialogPreview() {
    MindExpenseTheme {
        ConfirmGoBackDialog(
            isShowing = true,
            onDismissDialog = {},
            onClickConfirmButton = {},
        )
    }
}