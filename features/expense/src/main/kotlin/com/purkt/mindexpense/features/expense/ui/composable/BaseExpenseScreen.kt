package com.purkt.mindexpense.features.expense.ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.InputField
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.composable.drawVerticalScrollBar
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.core.ui.expense.composable.ExpenseItem
import kotlinx.coroutines.launch
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
                    .weight(1f)
                    .drawVerticalScrollBar(
                        scrollState = scrollState,
                        scrollBarColor = MaterialTheme.colorScheme.tertiary
                    )
                    .verticalScroll(scrollState)
                    .padding(dimensionResource(id = R.dimen.spacer_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_m))
            ) {
                var shouldExpandShowCase by rememberSaveable { mutableStateOf(false) }

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
                    isExpandedShowCase = shouldExpandShowCase,
                )
                HorizontalDivider()

                InputPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = title,
                    onTitleChanged = onTitleChanged,
                    isTitleError = isTitleError,
                    maxTitleLength = maxTitleLength,
                    recipient = recipient,
                    onRecipientChanged = onRecipientChanged,
                    isRecipientError = isRecipientError,
                    maxRecipientLength = maxRecipientLength,
                    displayAmount = displayAmount,
                    onAmountChanged = onAmountChanged,
                    isAmountError = isAmountError,
                    paidAt = paidAt,
                    onPaidAtChanged = onPaidAtChanged,
                    note = note,
                    onNoteChanged = onNoteChanged,
                    isNoteError = isNoteError,
                    maxNoteLength = maxNoteLength,
                    onUpdateExpandShowCase = { shouldExpandShowCase = it }
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
    isExpandedShowCase: Boolean = false,
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
            isExpanded = isExpandedShowCase,
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
private fun InputPager(
    modifier: Modifier = Modifier,
    title: String = "",
    onTitleChanged: (String) -> Unit = {},
    isTitleError: Boolean = false,
    maxTitleLength: Int? = null,
    recipient: String = "",
    onRecipientChanged: (String) -> Unit = {},
    isRecipientError: Boolean = false,
    maxRecipientLength: Int? = null,
    displayAmount: String = "",
    onAmountChanged: (String) -> Unit = {},
    isAmountError: Boolean = false,
    paidAt: LocalDateTime = LocalDateTime.now(),
    onPaidAtChanged: (LocalDateTime) -> Unit = {},
    note: String = "",
    onNoteChanged: (String) -> Unit = {},
    isNoteError: Boolean = false,
    maxNoteLength: Int? = null,
    onUpdateExpandShowCase: (Boolean) -> Unit = {},
) {
    val viewTypesCollapse = listOf(
        InputPagerViewType.TITLE,
        InputPagerViewType.RECIPIENT,
        InputPagerViewType.AMOUNT,
    )
    val viewTypesExpanded = listOf(
        InputPagerViewType.PAID_AT,
        InputPagerViewType.NOTE,
    )
    val viewTypesInOrder = viewTypesCollapse + viewTypesExpanded

    val (
        focusRequesterTitle,
        focusRequesterRecipient,
        focusRequesterAmount,
        focusRequesterNote,
    ) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val hideImeKeyboard = {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    val coroutineScope = rememberCoroutineScope()
    val totalPage = viewTypesInOrder.size
    val state = rememberPagerState { totalPage }
    var currentViewType by rememberSaveable { mutableStateOf(viewTypesInOrder.first()) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_m))
    ) {
        // Update expand show case state
        LaunchedEffect(state.currentPage) {
            viewTypesInOrder.getOrNull(state.currentPage)?.let { currentViewType = it }
        }
        LaunchedEffect(currentViewType) {
            onUpdateExpandShowCase.invoke(currentViewType in viewTypesExpanded)

            // Reset all focus
            try {
                when (currentViewType) {
                    InputPagerViewType.TITLE -> {
                        focusRequesterTitle.requestFocus()
                    }
                    InputPagerViewType.RECIPIENT -> {
                        focusRequesterRecipient.requestFocus()
                    }
                    InputPagerViewType.AMOUNT -> {
                        focusRequesterAmount.requestFocus()
                    }
                    InputPagerViewType.PAID_AT -> {
                        hideImeKeyboard.invoke()
                    }
                    InputPagerViewType.NOTE -> {
                        focusRequesterNote.requestFocus()
                    }
                }
            } catch (_: Throwable) { }
        }

        // Pager controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val targetPage = (state.currentPage - 1).coerceAtLeast(0)
                        state.animateScrollToPage(targetPage)
                    }
                },
                enabled = state.currentPage > 0
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.size_xl))
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(dimensionResource(R.dimen.spacer_xs)),
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Left arrow for input pager",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = dimensionResource(R.dimen.spacer_m),
                        vertical = dimensionResource(R.dimen.spacer_xs),
                    )
                ,
                style = MaterialTheme.typography.titleSmall,
                text = "${state.currentPage + 1}/$totalPage",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val targetPage = (state.currentPage + 1).coerceAtMost(totalPage - 1)
                        state.animateScrollToPage(targetPage)
                    }
                },
                enabled = state.currentPage < totalPage - 1
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.size_xl))
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(dimensionResource(R.dimen.spacer_xs)),
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Right arrow for input pager",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        // Pager
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = state,
            verticalAlignment = Alignment.Top,
            pageSpacing = dimensionResource(R.dimen.spacer_l)
        ) { page ->
            when (page) {
                InputPagerViewType.TITLE.ordinal -> {
                    // Title
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = onTitleChanged,
                        maxLength = maxTitleLength,
                        labelHint = stringResource(id = R.string.expense_item_title_hint),
                        isError = isTitleError,
                        errorText = stringResource(id = R.string.expense_item_title_error),
                        focusRequester = focusRequesterTitle,
                    )
                }
                InputPagerViewType.RECIPIENT.ordinal -> {
                    // Recipient
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = recipient,
                        onValueChange = onRecipientChanged,
                        maxLength = maxRecipientLength,
                        labelHint = stringResource(id = R.string.expense_item_recipient_hint),
                        isError = isRecipientError,
                        errorText = stringResource(id = R.string.expense_item_recipient_error),
                        focusRequester = focusRequesterRecipient,
                    )
                }
                InputPagerViewType.AMOUNT.ordinal -> {
                    // Amount
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = displayAmount,
                        onValueChange = onAmountChanged,
                        labelHint = stringResource(id = R.string.expense_item_amount_hint),
                        isError = isAmountError,
                        errorText = stringResource(id = R.string.expense_item_amount_error),
                        focusRequester = focusRequesterAmount,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    )
                }
                InputPagerViewType.PAID_AT.ordinal -> {
                    // Paid at date and time
                    PaidAtDateTimePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.spacer_m)),
                        paidAt = paidAt,
                        onPaidAtChanged = onPaidAtChanged,
                    )
                }
                InputPagerViewType.NOTE.ordinal -> {
                    // Detail note (Optional)
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = note,
                        onValueChange = onNoteChanged,
                        maxLength = maxNoteLength,
                        labelHint = stringResource(id = R.string.expense_item_note_hint),
                        isError = isNoteError,
                        errorText = stringResource(id = R.string.expense_item_note_error),
                        minLines = 5,
                        focusRequester = focusRequesterNote,
                    )
                }
            }
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

private enum class InputPagerViewType {
    TITLE, RECIPIENT, AMOUNT, PAID_AT, NOTE;
}