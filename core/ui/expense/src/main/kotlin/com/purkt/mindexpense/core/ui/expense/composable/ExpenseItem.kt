package com.purkt.mindexpense.core.ui.expense.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.purkt.mindexpense.core.data.common.DATE_FULL_PATTERN
import com.purkt.mindexpense.core.data.common.TIME_12_HOUR_FORMAT_PATTERN
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.data.common.toDateTimeStringOrNull
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreviewAllScales
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import java.time.LocalDateTime

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = dimensionResource(R.dimen.size_l),
    contentPadding: PaddingValues = PaddingValues(dimensionResource(R.dimen.spacer_l)),
    shouldExpanded: Boolean = false,
    data: Expense,
    isPreviewMode: Boolean = false,
    headerContainerColor: Color = MaterialTheme.colorScheme.primary,
    headerContentColor: Color = contentColorFor(headerContainerColor),
    detailContainerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    detailContentColor: Color = contentColorFor(detailContainerColor),
    onDeleteItem: () -> Unit = {},
) {
    var isExpanded: Boolean by rememberSaveable { mutableStateOf(shouldExpanded) }
    LaunchedEffect(key1 = shouldExpanded) { isExpanded = shouldExpanded }

    ExpenseItem(
        modifier = modifier,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
        isExpanded = isExpanded,
        onIsExpandedChanged = { isExpanded = it },
        title = data.title,
        recipient = data.recipient,
        amount = data.amount,
        paidAt = data.paidAt,
        note = data.note,
        isPreviewMode = isPreviewMode,
        headerContainerColor = headerContainerColor,
        headerContentColor = headerContentColor,
        detailContainerColor = detailContainerColor,
        detailContentColor = detailContentColor,
        onDeleteItem = onDeleteItem,
    )
}

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = dimensionResource(id = R.dimen.size_l),
    contentPadding: PaddingValues = PaddingValues(dimensionResource(id = R.dimen.spacer_l)),
    isExpanded: Boolean = false,
    onIsExpandedChanged: (Boolean) -> Unit = {},
    title: String,
    recipient: String,
    amount: Double,
    paidAt: LocalDateTime,
    note: String,
    isPreviewMode: Boolean = false,
    isTitleError: Boolean = false,
    isRecipientError: Boolean = false,
    isAmountError: Boolean = false,
    isNoteError: Boolean = false,
    headerContainerColor: Color = MaterialTheme.colorScheme.primary,
    headerContentColor: Color = contentColorFor(headerContainerColor),
    detailContainerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    detailContentColor: Color = contentColorFor(detailContainerColor),
    onDeleteItem: () -> Unit = {},
) {
    val bottomCornerRadius by animateDpAsState(
        targetValue = if (isExpanded) cornerRadius / 2 else cornerRadius
    )
    val bottomHeaderCornerRadius by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else cornerRadius
    )
    CompositionLocalProvider(LocalContentColor provides headerContentColor) {
        Column(
            modifier = Modifier
                .then(modifier)
                // This will be a default width option in case that target modifier didn't set any width option
                .width(IntrinsicSize.Max)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = cornerRadius,
                        bottomStart = bottomCornerRadius,
                        bottomEnd = bottomCornerRadius,
                    )
                )
        ) {
            // Header
            HeaderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = bottomHeaderCornerRadius,
                            bottomEnd = bottomHeaderCornerRadius,
                        )
                    )
                    .background(headerContainerColor)
                    .clickable { onIsExpandedChanged.invoke(!isExpanded) }
                    .padding(contentPadding),
                title = title,
                recipient = recipient,
                amount = amount,
                paidAt = paidAt,
                isPreviewMode = isPreviewMode,
                isTitleError = isTitleError,
                isRecipientError = isRecipientError,
                isAmountError = isAmountError,
            )

            // Detail
            CompositionLocalProvider(LocalContentColor provides detailContentColor) {
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = isExpanded,
                ) {
                    DetailSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(detailContainerColor)
                            .padding(contentPadding),
                        note = note,
                        paidAt = paidAt,
                        isPreviewMode =  isPreviewMode,
                        isNoteError = isNoteError,
                        onDeleteItem = onDeleteItem,
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    recipient: String,
    amount: Double,
    paidAt: LocalDateTime,
    isPreviewMode: Boolean = false,
    isTitleError: Boolean = false,
    isRecipientError: Boolean = false,
    isAmountError: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleRecipientSection(
            modifier = Modifier.weight(1f),
            title = title,
            recipient = recipient,
            isPreviewMode = isPreviewMode,
            isTitleError = isTitleError,
            isRecipientError = isRecipientError,
        )
        AmountPaidAtSection(
            modifier = Modifier.weight(1f),
            amount = amount,
            paidAt = paidAt,
            isAmountError = isAmountError,
        )
    }
}

@Composable
private fun TitleRecipientSection(
    modifier: Modifier = Modifier,
    title: String,
    recipient: String,
    isPreviewMode: Boolean = false,
    isTitleError: Boolean = false,
    isRecipientError: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
    ) {
        Text(
            modifier = Modifier.wrapAsError(isError = isTitleError),
            style = MaterialTheme.typography.titleLarge,
            text = if (isPreviewMode) {
                title.ifBlank { stringResource(id = R.string.expense_item_title_hint) }
            } else title,
            fontWeight = FontWeight.Bold,
            color = LocalContentColor.current.onCondition(
                showAsError = isTitleError,
                showAsHint = isPreviewMode && title.isBlank(),
            ),
        )
        Text(
            modifier = Modifier.wrapAsError(isError = isRecipientError),
            style = MaterialTheme.typography.labelLarge,
            text = buildAnnotatedString {
                val label = stringResource(id = R.string.expense_recipent_label)
                withStyle(
                    style = MaterialTheme.typography.labelLarge
                        .toSpanStyle()
                        .copy(fontWeight = FontWeight.Bold)
                ) {
                    append("$label: ")
                }
                withStyle(
                    style = MaterialTheme.typography.labelLarge
                        .toSpanStyle()
                        .copy(
                            color = LocalContentColor.current.onCondition(
                                showAsError = isRecipientError,
                                showAsHint = isPreviewMode && recipient.isBlank(),
                            ),
                        )
                ) {
                    append(
                        if (isPreviewMode) {
                            recipient.ifBlank { stringResource(id = R.string.expense_item_recipient_hint) }
                        } else recipient
                    )
                }
            },
            color = LocalContentColor.current.onCondition(
                showAsError = isRecipientError,
                showAsHint = false,
            ),
        )
    }
}

@Composable
private fun AmountPaidAtSection(
    modifier: Modifier = Modifier,
    amount: Double, paidAt: LocalDateTime,
    isAmountError: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
    ) {
        val displayAmount = amount.formatCurrencyOrNull() ?: "-"
        val baseBigStyle = MaterialTheme.typography.titleLarge
        val baseSmallStyle = MaterialTheme.typography.bodySmall

        Text(
            modifier = Modifier.wrapAsError(isError = isAmountError),
            style = if (displayAmount.length > 10) baseSmallStyle else baseBigStyle,
            text = displayAmount,
            fontWeight = FontWeight.Bold,
            color = LocalContentColor.current.onCondition(
                showAsError = isAmountError,
                showAsHint = false,
            ),
        )
        PaidAtTimeText(paidAt = paidAt)
    }
}

@Composable
private fun PaidAtTimeText(paidAt: LocalDateTime) {
    val time by remember(key1 = paidAt) {
        mutableStateOf(paidAt.toDateTimeStringOrNull(pattern = TIME_12_HOUR_FORMAT_PATTERN) ?: "-")
    }

    Text(style = MaterialTheme.typography.labelLarge, text = time)
}

@Composable
private fun DetailSection(
    modifier: Modifier = Modifier,
    note: String,
    paidAt: LocalDateTime,
    isPreviewMode: Boolean = false,
    isNoteError: Boolean = false,
    onDeleteItem: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l))
    ) {
        // Detail
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DescriptionSection(
                note = note,
                paidAt = paidAt,
                isPreviewMode = isPreviewMode,
                isNoteError = isNoteError,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {  },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Delete icon",
                    tint = LocalContentColor.current,
                )
            }
        }

        // Delete
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.size_l)))
                    .clickable(onClick = onDeleteItem)
                    .padding(
                        horizontal = dimensionResource(R.dimen.spacer_l),
                        vertical = dimensionResource(R.dimen.spacer_s)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon",
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(id = R.string.expense_delete),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun DescriptionSection(
    note: String,
    paidAt: LocalDateTime,
    isPreviewMode: Boolean = false,
    isNoteError: Boolean,
) {
    val displayPaidAt by remember(key1 = paidAt) {
        mutableStateOf(paidAt.toDateTimeStringOrNull(pattern = DATE_FULL_PATTERN) ?: "-")
    }

    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s))) {
        if (isPreviewMode || note.isNotBlank()) {
            Text(
                modifier = Modifier.wrapAsError(isError = isNoteError),
                style = MaterialTheme.typography.bodyMedium,
                text = if (isPreviewMode) {
                    note.ifBlank { stringResource(id = R.string.expense_item_note_hint) }
                } else note,
                color = LocalContentColor.current.onCondition(
                    showAsError = isNoteError,
                    showAsHint = isPreviewMode && note.isBlank(),
                ),
            )
        }
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = buildAnnotatedString {
                val label = stringResource(id = R.string.expense_paid_at_label)
                withStyle(
                    style = MaterialTheme.typography.bodyMedium
                        .toSpanStyle()
                        .copy(fontWeight = FontWeight.Bold)
                ) {
                    append("$label: ")
                }
                append(displayPaidAt)
            },
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewExpenseItemExpanded(
    @PreviewParameter(PreviewExpenseProvider::class) expense: Expense,
) {
    MindExpenseTheme {
        Surface {
            ExpenseItem(
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.spacer_l)),
                shouldExpanded = true,
                data = expense,
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewExpenseItemDefaultWithExpense(
    @PreviewParameter(PreviewExpenseProvider::class) expense: Expense,
) {
    MindExpenseTheme {
        Surface {
            ExpenseItem(
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.spacer_l)),
                data = expense,
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewExpenseItemDefaultWithRawData() {
    MindExpenseTheme {
        Surface {
            ExpenseItem(
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.spacer_l)),
                data = mockExpenses.first().copy(
                    title = "",
                    recipient = "",
                    note = "",
                ),
                shouldExpanded = true,
                isPreviewMode = true,
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewExpenseItemError() {
    MindExpenseTheme {
        Surface {
            ExpenseItem(
                modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.spacer_l)),
                isExpanded = true,
                title = "",
                recipient = "",
                amount = 0.0,
                paidAt = LocalDateTime.now(),
                note = "",
                isTitleError = true,
                isRecipientError = true,
                isAmountError = true,
                isNoteError = true,
                isPreviewMode = true,
            )
        }
    }
}

@MindExpensePreviewAllScales
@Composable
private fun PreviewExpenseItemAllScales() {
    MindExpenseTheme {
        Surface {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.spacer_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
            ) {
                ExpenseItem(
                    modifier = Modifier.fillMaxWidth(),
                    data = mockExpenseAmountBeforeResized,
                    isPreviewMode = true,
                )

                ExpenseItem(
                    modifier = Modifier.fillMaxWidth(),
                    data = mockExpenseBiggestAmountResized,
                    isPreviewMode = true,
                )
            }
        }
    }
}

@Composable
private fun Color.onCondition(
    showAsError: Boolean = false,
    showAsHint: Boolean,
): Color {
    return when {
        showAsError -> MaterialTheme.colorScheme.error
        showAsHint -> copy(alpha = 0.5f)
        else -> this
    }
}

@Composable
private fun Modifier.wrapAsError(
    isError: Boolean,
    nonErrorContainerColor: Color = Color.Transparent,
    errorContainerColor: Color = MaterialTheme.colorScheme.onError
): Modifier {
    return this
        .run { if (isError) { clip(RoundedCornerShape(dimensionResource(R.dimen.size_s))) } else this }
        .background(if (isError) errorContainerColor else nonErrorContainerColor)
}