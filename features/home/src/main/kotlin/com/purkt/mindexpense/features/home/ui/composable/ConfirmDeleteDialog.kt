package com.purkt.mindexpense.features.home.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.purkt.mindexpense.core.ui.common.composable.MindExpenseDialog
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
internal fun ConfirmDeleteDialog(
    isShowing: Boolean,
    message: String? = null,
    onDismiss: () -> Unit,
    onClickConfirmButton: () -> Unit,
) {
    if (isShowing) {
        MindExpenseDialog(
            title = stringResource(id = R.string.expense_confirm_delete_dialog_title),
            message = if (!message.isNullOrBlank()) {
                stringResource(id = R.string.expense_confirm_delete_dialog_message_template, message)
            } else "",
            onClickDismiss = onDismiss,
            onClickConfirm = onClickConfirmButton,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewConfirmDeleteDialog() {
    MindExpenseTheme {
        ConfirmDeleteDialog(
            isShowing = true,
            message = "Lunch break with family.",
            onDismiss = {},
            onClickConfirmButton = {}
        )
    }
}