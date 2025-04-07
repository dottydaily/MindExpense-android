package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun MindExpenseDialog(
    title: String,
    message: String? = null,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
    confirmButtonText: String = stringResource(id = R.string.common_ok_label),
    dismissButtonText: String = stringResource(R.string.common_cancel_label),
    shouldShowDismissButton: Boolean = true,
) {
    Dialog(onDismissRequest = onClickDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = 300.dp)
                    .width(IntrinsicSize.Max)
                    .padding(
                        horizontal = dimensionResource(R.dimen.spacer_s),
                        vertical = dimensionResource(R.dimen.spacer_s),
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.spacer_m),
                            start = dimensionResource(R.dimen.spacer_l),
                            end = dimensionResource(R.dimen.spacer_l),
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_m))
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = title,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )

                    if (!message.isNullOrBlank()) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = message,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_l)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (shouldShowDismissButton) {
                        IconTextButton(
                            modifier = Modifier.weight(1f),
                            onClick = onClickDismiss,
                            text = dismissButtonText,
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Icon of dismiss button in dialog",
                                )
                            },
                            padding = PaddingValues(dimensionResource(R.dimen.spacer_s)),
                        )
                    }
                    IconTextButton(
                        modifier = Modifier.weight(1f),
                        onClick = onClickConfirm,
                        text = confirmButtonText,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Icon of confirm button in dialog",
                            )
                        },
                        padding = PaddingValues(dimensionResource(R.dimen.spacer_s)),
                    )
                }
            }
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewMindExpenseDialog() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MindExpenseDialog(
                title = "Do you want to confirm to delete?",
                message = "You action cannot be undone.",
                onClickConfirm = {},
                onClickDismiss = {},
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewMindExpenseDialogHideCancel() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MindExpenseDialog(
                title = "Operation success.",
                message = "You are ready to continue.",
                onClickConfirm = {},
                onClickDismiss = {},
                shouldShowDismissButton = false,
            )
        }
    }
}

@MindExpensePreviewAllScales
@Composable
private fun PreviewMindExpenseDialogScales() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MindExpenseDialog(
                title = "คุณยืนยันที่จะลบใช่หรือไม่?",
                message = "คุณจะไม่สามารถยกเลิกการกระทำนี้ได้",
                onClickConfirm = {},
                onClickDismiss = {},
                confirmButtonText = stringResource(id = R.string.common_ok_label),
                dismissButtonText = stringResource(id = R.string.common_cancel_label),
            )
        }
    }
}