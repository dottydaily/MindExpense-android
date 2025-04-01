package com.purkt.mindexpense.features.home.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.core.ui.resources.R

@Composable
internal fun ExpenseItem(
    modifier: Modifier = Modifier,
    data: Expense,
    headerContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    headerContentColor: Color = contentColorFor(headerContainerColor),
    detailContainerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    detailContentColor: Color = contentColorFor(detailContainerColor),
    onDeleteItem: (Expense) -> Unit = {},
) {
    var isExpanded: Boolean by rememberSaveable { mutableStateOf(false) }

    ExpenseItem(
        modifier = modifier,
        isExpanded = isExpanded,
        onIsExpandedChanged = { isExpanded = it },
        data = data,
        headerContainerColor = headerContainerColor,
        headerContentColor = headerContentColor,
        detailContainerColor = detailContainerColor,
        detailContentColor = detailContentColor,
        onDeleteItem = onDeleteItem,
    )
}

@Composable
internal fun ExpenseItem(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onIsExpandedChanged: (Boolean) -> Unit = {},
    data: Expense,
    headerContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    headerContentColor: Color = contentColorFor(headerContainerColor),
    detailContainerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    detailContentColor: Color = contentColorFor(detailContainerColor),
    onDeleteItem: (Expense) -> Unit = {},
) {
    val topCornerRadius = 16.dp
    val bottomCornerRadius by animateDpAsState(
        targetValue = if (isExpanded) topCornerRadius / 2 else topCornerRadius
    )
    val bottomHeaderCornerRadius by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else topCornerRadius
    )
    CompositionLocalProvider(LocalContentColor provides headerContentColor) {
        Column(
            modifier = Modifier
                .then(modifier)
                // This will be a default width option in case that target modifier didn't set any width option
                .width(IntrinsicSize.Max)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = topCornerRadius,
                        topEnd = topCornerRadius,
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
                    .padding(16.dp),
                expense = data,
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
                            .padding(16.dp),
                        expense = data,
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
    expense: Expense,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleRecipientSection(title = expense.title, recipient = expense.recipient)
        Spacer(modifier = Modifier.weight(1f))
        AmountPaidAtSection(expense = expense)
    }
}

@Composable
private fun TitleRecipientSection(title: String, recipient: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = title,
            fontWeight = FontWeight.Bold,
        )
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = buildAnnotatedString {
                val label = stringResource(id = R.string.home_expense_recipent_label)
                withStyle(
                    style = MaterialTheme.typography.labelLarge
                        .toSpanStyle()
                        .copy(fontWeight = FontWeight.Bold)
                ) {
                    append("$label: ")
                }
                append(recipient)
            },
        )
    }
}

@Composable
private fun AmountPaidAtSection(expense: Expense) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = expense.amount.formatCurrencyOrNull() ?: "-",
            fontWeight = FontWeight.Bold,
        )
        PaidAtTimeText(expense = expense)
    }
}

@Composable
private fun PaidAtTimeText(expense: Expense) {
    val time by remember(key1 = expense) {
        mutableStateOf(expense.getPaidAtTimeStringOrNull() ?: "-")
    }

    Text(style = MaterialTheme.typography.labelLarge, text = time)
}

@Composable
private fun DetailSection(
    modifier: Modifier = Modifier,
    expense: Expense,
    onDeleteItem: (Expense) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Detail
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DescriptionSection(expense = expense)
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
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onDeleteItem.invoke(expense)
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon",
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(id = R.string.home_expense_delete),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun DescriptionSection(expense: Expense) {
    val paidAt by remember(key1 = expense) {
        mutableStateOf(expense.getPaidAtFullDateStringOrNull() ?: "-")
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (expense.note.isNotBlank()) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = expense.note,
            )
        }
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = buildAnnotatedString {
                val label = stringResource(id = R.string.home_expense_paid_at_label)
                withStyle(
                    style = MaterialTheme.typography.bodyMedium
                        .toSpanStyle()
                        .copy(fontWeight = FontWeight.Bold)
                ) {
                    append("$label: ")
                }
                append(paidAt)
            },
        )
    }
}

@Preview
@Preview(locale = "th")
@Composable
private fun PreviewExpenseItemExpanded(
    @PreviewParameter(PreviewExpenseProvider::class) expense: Expense,
) {
    MindExpenseTheme {
        ExpenseItem(
            modifier = Modifier.fillMaxWidth(),
            isExpanded = true,
            data = expense,
        )
    }
}

@Preview
@Composable
private fun PreviewExpenseItemDefault(
    @PreviewParameter(PreviewExpenseProvider::class) expense: Expense,
) {
    MindExpenseTheme {
        ExpenseItem(
            modifier = Modifier.fillMaxWidth(),
            data = expense,
        )
    }
}
