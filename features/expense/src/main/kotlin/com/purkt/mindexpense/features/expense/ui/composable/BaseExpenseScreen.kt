package com.purkt.mindexpense.features.expense.ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.core.ui.expense.composable.ExpenseItem
import java.time.LocalDateTime

internal enum class ExpenseScreenType {
    ADD,
    EDIT;
}

@Composable
internal fun BaseExpenseScreen(
    mode: ExpenseScreenType = ExpenseScreenType.ADD,
    title: String = "",
    onTitleChanged: (String) -> Unit = {},
    isTitleError: Boolean = false,
    recipient: String = "",
    onRecipientChanged: (String) -> Unit = {},
    isRecipientError: Boolean = false,
    amount: Double = 0.0,
    onAmountChanged: (String) -> Unit = {},
    isAmountError: Boolean = false,
    paidAt: LocalDateTime = LocalDateTime.now(),
    note: String = "",
    onNoteChanged: (String) -> Unit = {},
    isNoteError: Boolean = false,
    onGoBack: () -> Unit = {},
) {
    BackHandler { onGoBack.invoke() }

    val surfaceColor = MaterialTheme.colorScheme.surface
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = surfaceColor,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l))
        ) {
            val displayAmount by rememberSaveable(key = amount.toString()) {
                mutableStateOf(amount.formatCurrencyOrNull() ?: "0")
            }

            TitleBar(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacer_l)),
                mode = mode,
                background = MaterialTheme.colorScheme.primary,
            )
            ExpenseItemShowCase(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l)),
                title = title,
                recipient = recipient,
                amount = amount,
                paidAt = paidAt,
                note = note,
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l))
            )
            ExpenseItemInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l)),
                value = title,
                onValueChange = onTitleChanged,
                labelHint = stringResource(id = R.string.expense_item_title_hint),
                isError = isTitleError,
                errorText = stringResource(id = R.string.expense_item_title_error),
            )
            ExpenseItemInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l)),
                value = recipient,
                onValueChange = onRecipientChanged,
                labelHint = stringResource(id = R.string.expense_item_recipient_hint),
                isError = isRecipientError,
                errorText = stringResource(id = R.string.expense_item_recipient_error),
            )
            ExpenseItemInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l)),
                value = displayAmount,
                onValueChange = onAmountChanged,
                labelHint = stringResource(id = R.string.expense_item_amount_hint),
                isError = isAmountError,
                errorText = stringResource(id = R.string.expense_item_amount_error),
            )
            ExpenseItemInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.spacer_l)),
                value = note,
                onValueChange = onNoteChanged,
                labelHint = stringResource(id = R.string.expense_item_note_hint),
                isError = isNoteError,
                errorText = stringResource(id = R.string.expense_item_note_error),
            )
        }
    }
}

@Composable
private fun TitleBar(
    modifier: Modifier = Modifier,
    mode: ExpenseScreenType,
    background: Color,
) {
    CompositionLocalProvider(LocalContentColor provides contentColorFor(background)) {
        Text(
            modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topEndPercent = 50,
                        bottomEndPercent = 50,
                    )
                )
                .background(background)
                .padding(
                    top = dimensionResource(R.dimen.spacer_s),
                    start = dimensionResource(R.dimen.spacer_s),
                    end = dimensionResource(R.dimen.spacer_m),
                    bottom = dimensionResource(R.dimen.spacer_s),
                ),
            style = MaterialTheme.typography.headlineSmall,
            text = when (mode) {
                ExpenseScreenType.ADD -> stringResource(id = R.string.expense_add_title)
                ExpenseScreenType.EDIT -> stringResource(id = R.string.expense_edit_title)
            },
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ExpenseItemShowCase(
    modifier: Modifier = Modifier,
    title: String,
    isTitleError: Boolean = false,
    recipient: String,
    isRecipientError: Boolean = false,
    amount: Double,
    isAmountError: Boolean = false,
    paidAt: LocalDateTime,
    note: String,
    isNoteError: Boolean = false,
) {
    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        ExpenseItem(
            modifier = Modifier.fillMaxWidth(),
            isExpanded = true,
            title = title,
            isTitleError = isTitleError,
            recipient = recipient,
            isRecipientError = isRecipientError,
            amount = amount,
            isAmountError = isAmountError,
            paidAt = paidAt,
            note = note,
            isNoteError = isNoteError,
            isPreviewMode = true,
        )
    }
}

@Composable
private fun ExpenseItemInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelHint: String = "",
    isError: Boolean,
    errorText: String = "",
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelHint) },
            isError = isError,
        )
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.spacer_s)),
            visible = isError && errorText.isNotBlank(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = errorText,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseExpenseScreenAdd() {
    MindExpenseTheme {
        var title by rememberSaveable { mutableStateOf("") }
        var recipient by rememberSaveable { mutableStateOf("") }
        var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
        var note by rememberSaveable { mutableStateOf("") }

        BaseExpenseScreen(
            mode = ExpenseScreenType.ADD,
            title = title,
            onTitleChanged = { title = it },
            isTitleError = title.length > 30,
            recipient = recipient,
            onRecipientChanged = { recipient = it },
            isRecipientError = recipient.length > 30,
            amount = amount,
            onAmountChanged = { amount = it.toDoubleOrNull() ?: 0.0 },
            isAmountError = amount > 999999999.99,
            note = note,
            onNoteChanged = { note = it },
            isNoteError = note.length > 200,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseExpenseScreenEdit() {
    MindExpenseTheme {
        var title by rememberSaveable { mutableStateOf("") }
        var recipient by rememberSaveable { mutableStateOf("") }
        var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
        var note by rememberSaveable { mutableStateOf("") }

        BaseExpenseScreen(
            mode = ExpenseScreenType.EDIT,
            title = title,
            onTitleChanged = { title = it },
            isTitleError = title.length > 30,
            recipient = recipient,
            onRecipientChanged = { recipient = it },
            isRecipientError = recipient.length > 30,
            amount = amount,
            onAmountChanged = { amount = it.toDoubleOrNull() ?: 0.0 },
            isAmountError = amount > 999999999.99,
            note = note,
            onNoteChanged = { note = it },
            isNoteError = note.length > 200,
        )
    }
}