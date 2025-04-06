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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.DateAndTimePicker
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.composable.drawVerticalScrollBar
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
    maxTitleLength: Int? = null,
    recipient: String = "",
    onRecipientChanged: (String) -> Unit = {},
    isRecipientError: Boolean = false,
    maxRecipientLength: Int? = null,
    amount: Double = 0.0,
    displayAmount: String = "",
    onAmountChanged: (String) -> Unit = {},
    isAmountError: Boolean = false,
    paidAt: LocalDateTime = LocalDateTime.now(),
    onPaidAtChanged: (LocalDateTime) -> Unit = {},
    note: String = "",
    onNoteChanged: (String) -> Unit = {},
    isNoteError: Boolean = false,
    maxNoteLength: Int? = null,
    onClickSubmitButton: () -> Unit = {},
    onGoBack: () -> Unit = {},
) {
    BackHandler { onGoBack.invoke() }

    val surfaceColor = MaterialTheme.colorScheme.surface
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            ButtonBarLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.spacer_l),
                        vertical = dimensionResource(id = R.dimen.spacer_m),
                    ),
                onClickCancelButton = onGoBack,
                onClickSubmitButton = onClickSubmitButton,
            )
        },
        containerColor = surfaceColor,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            val scrollState = rememberScrollState()

            TitleBar(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(dimensionResource(R.dimen.spacer_l)),
                mode = mode,
            )

            Column(
                modifier = Modifier
                    .drawVerticalScrollBar(
                        scrollState = scrollState,
                        scrollBarColor = MaterialTheme.colorScheme.tertiary
                    )
                    .verticalScroll(scrollState)
                    .padding(dimensionResource(id = R.dimen.spacer_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l))
            ) {
                ExpenseItemShowCase(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    isTitleError = isTitleError,
                    recipient = recipient,
                    isRecipientError = isRecipientError,
                    amount = amount,
                    isAmountError = isAmountError,
                    paidAt = paidAt,
                    note = note,
                    isNoteError = isNoteError,
                )
                HorizontalDivider()
                
                // Title
                ExpenseItemInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = onTitleChanged,
                    maxLength = maxTitleLength,
                    labelHint = stringResource(id = R.string.expense_item_title_hint),
                    isError = isTitleError,
                    errorText = stringResource(id = R.string.expense_item_title_error),
                )

                // Recipient
                ExpenseItemInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = recipient,
                    onValueChange = onRecipientChanged,
                    maxLength = maxRecipientLength,
                    labelHint = stringResource(id = R.string.expense_item_recipient_hint),
                    isError = isRecipientError,
                    errorText = stringResource(id = R.string.expense_item_recipient_error),
                )

                // Amount
                ExpenseItemInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = displayAmount,
                    onValueChange = onAmountChanged,
                    labelHint = stringResource(id = R.string.expense_item_amount_hint),
                    isError = isAmountError,
                    errorText = stringResource(id = R.string.expense_item_amount_error),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )

                // Paid at date and time
                PaidAtDateTimePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.size_m)))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(dimensionResource(R.dimen.spacer_m))
                        .padding(vertical = dimensionResource(R.dimen.spacer_m)),
                    buttonColor = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    paidAt = paidAt,
                    onPaidAtChanged = onPaidAtChanged,
                )
                
                // Detail note (Optional)
                ExpenseItemInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = note,
                    onValueChange = onNoteChanged,
                    maxLength = maxNoteLength,
                    labelHint = stringResource(id = R.string.expense_item_note_hint),
                    isError = isNoteError,
                    errorText = stringResource(id = R.string.expense_item_note_error),
                    minLines = 5,
                )
            }
        }
    }
}

@Composable
private fun TitleBar(
    modifier: Modifier = Modifier,
    mode: ExpenseScreenType,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
        text = when (mode) {
            ExpenseScreenType.ADD -> stringResource(id = R.string.expense_add_title)
            ExpenseScreenType.EDIT -> stringResource(id = R.string.expense_edit_title)
        },
        fontWeight = FontWeight.Bold,
    )
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
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s))
    ) {
        val chipContainerColor = MaterialTheme.colorScheme.primary
        val chipContentColor = contentColorFor(chipContainerColor)
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(50))
                .background(chipContainerColor)
                .padding(
                    horizontal = dimensionResource(R.dimen.spacer_m),
                    vertical = dimensionResource(R.dimen.spacer_xs),
                )
            ,
            style = MaterialTheme.typography.titleSmall,
            text = stringResource(id = R.string.expense_item_preview_label),
            fontWeight = FontWeight.Bold,
            color = chipContentColor,
        )
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
    maxLength: Int? = null,
    labelHint: String = "",
    isError: Boolean,
    errorText: String = "",
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            AnimatedVisibility(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacer_s)),
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

            Spacer(modifier = Modifier.weight(1f))

            Text(
                style = MaterialTheme.typography.labelLarge,
                text = if (maxLength != null) {
                    "${value.length}/$maxLength"
                } else "${value.length}",
                color = if (maxLength != null && value.length > maxLength) {
                    MaterialTheme.colorScheme.error
                } else Color.Unspecified,
            )
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelHint) },
            isError = isError,
            keyboardOptions = keyboardOptions,
            minLines = minLines,
            maxLines = maxLines,
        )
    }
}

@Composable
private fun PaidAtDateTimePicker(
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(),
    paidAt: LocalDateTime,
    onPaidAtChanged: (LocalDateTime) -> Unit,
) {
    DateAndTimePicker(
        modifier = modifier,
        buttonColor = buttonColor,
        dateTime = paidAt,
        onDateTimeChanged = onPaidAtChanged,
    )
}

@Composable
private fun ButtonBarLayout(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    onClickSubmitButton: () -> Unit,
    onClickCancelButton: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(containerColor)
            .then(modifier)
            .width(IntrinsicSize.Min),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            CancelButton(onClick = onClickCancelButton)
            Spacer(modifier = Modifier.weight(1f))
            SaveButton(
                onClick = onClickSubmitButton,
                containerColor = contentColor,
                contentColor = containerColor,
            )
        }
    }
}

@Composable
private fun CancelButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentColor: Color = LocalContentColor.current,
) {
    TextButton(
        modifier = modifier.defaultMinSize(
            minWidth = dimensionResource(id = R.dimen.button_min_width),
            minHeight = dimensionResource(id = R.dimen.button_min_height),
        ),
        onClick = onClick,
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(id = R.string.expense_discard_label),
            fontWeight = FontWeight.Bold,
            color = contentColor,
        )
    }
}

@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color = contentColorFor(containerColor),
) {
    Button(
        modifier = modifier.defaultMinSize(
            minWidth = dimensionResource(id = R.dimen.button_min_width),
            minHeight = dimensionResource(id = R.dimen.button_min_height),
        ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_s)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Save icon of save button",
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.expense_save_label),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseExpenseScreenAdd() {
    MindExpenseTheme {
        var title by rememberSaveable { mutableStateOf("") }
        val maxTitleLength = 30
        var recipient by rememberSaveable { mutableStateOf("") }
        val maxRecipientLength = 20
        var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
        var note by rememberSaveable { mutableStateOf("") }
        val maxNoteLength = 200

        BaseExpenseScreen(
            mode = ExpenseScreenType.ADD,
            title = title,
            onTitleChanged = { title = it },
            isTitleError = title.length > maxTitleLength,
            maxTitleLength = maxTitleLength,
            recipient = recipient,
            onRecipientChanged = { recipient = it },
            isRecipientError = recipient.length > maxRecipientLength,
            maxRecipientLength = maxRecipientLength,
            amount = amount,
            onAmountChanged = { amount = it.toDoubleOrNull() ?: 0.0 },
            isAmountError = amount > 999999999.99,
            note = note,
            onNoteChanged = { note = it },
            isNoteError = note.length > maxNoteLength,
            maxNoteLength = maxNoteLength,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseExpenseScreenEdit() {
    MindExpenseTheme {
        var title by rememberSaveable { mutableStateOf("") }
        val maxTitleLength = 30
        var recipient by rememberSaveable { mutableStateOf("") }
        val maxRecipientLength = 20
        var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
        var note by rememberSaveable { mutableStateOf("") }
        val maxNoteLength = 200

        BaseExpenseScreen(
            mode = ExpenseScreenType.EDIT,
            title = title,
            onTitleChanged = { title = it },
            isTitleError = title.length > maxTitleLength,
            maxTitleLength = maxTitleLength,
            recipient = recipient,
            onRecipientChanged = { recipient = it },
            isRecipientError = recipient.length > maxRecipientLength,
            maxRecipientLength = maxRecipientLength,
            amount = amount,
            onAmountChanged = { amount = it.toDoubleOrNull() ?: 0.0 },
            isAmountError = amount > 999999999.99,
            note = note,
            onNoteChanged = { note = it },
            isNoteError = note.length > maxNoteLength,
            maxNoteLength = maxNoteLength,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseExpenseScreenForceError() {
    MindExpenseTheme {
        var title by rememberSaveable { mutableStateOf("") }
        val maxTitleLength = 30
        var recipient by rememberSaveable { mutableStateOf("") }
        val maxRecipientLength = 20
        var amount by rememberSaveable { mutableDoubleStateOf(0.0) }
        var note by rememberSaveable { mutableStateOf("") }
        val maxNoteLength = 200

        BaseExpenseScreen(
            mode = ExpenseScreenType.EDIT,
            title = title,
            onTitleChanged = { title = it },
            isTitleError = true,
            maxTitleLength = maxTitleLength,
            recipient = recipient,
            onRecipientChanged = { recipient = it },
            isRecipientError = true,
            maxRecipientLength = maxRecipientLength,
            amount = amount,
            onAmountChanged = { amount = it.toDoubleOrNull() ?: 0.0 },
            isAmountError = true,
            note = note,
            onNoteChanged = { note = it },
            isNoteError = true,
            maxNoteLength = maxNoteLength,
        )
    }
}