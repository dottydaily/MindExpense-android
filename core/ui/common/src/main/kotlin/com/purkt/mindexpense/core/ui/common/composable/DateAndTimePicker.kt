package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.purkt.mindexpense.core.data.common.DATE_TIME_12_HOUR_FORMAT_PATTERN
import com.purkt.mindexpense.core.data.common.toDateTimeStringOrNull
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimePicker(
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(),
    dateTime: LocalDateTime,
    onDateTimeChanged: (LocalDateTime) -> Unit,
) {
    val currentTimestampMillis: Long = remember(dateTime) {
        val zonedDateTime = dateTime.atZone(ZoneId.ofOffset("", ZoneOffset.UTC))
        zonedDateTime.toInstant().toEpochMilli()
    }
    var inPickingProcess by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_m)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val displayPaidAt by remember(dateTime) {
            val formatted = dateTime.toDateTimeStringOrNull(pattern = DATE_TIME_12_HOUR_FORMAT_PATTERN)
            mutableStateOf(formatted ?: "-")
        }

        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
            text = displayPaidAt,
            color = LocalContentColor.current,
            fontWeight = FontWeight.Bold,
        )
        Button(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = dimensionResource(id = R.dimen.button_min_width),
                    minHeight = dimensionResource(id = R.dimen.button_min_height),
                ),
            onClick = { inPickingProcess = true },
            colors = buttonColor,
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.expense_date_time_button_label),
                fontWeight = FontWeight.Bold,
            )
        }
    }

    if (inPickingProcess) {
        var shouldShowDatePicker by rememberSaveable { mutableStateOf(true) }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = currentTimestampMillis
        )
        var shouldShowTimePicker by rememberSaveable { mutableStateOf(false) }
        val timePickerState = rememberTimePickerState(
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute,
        )

        if (shouldShowDatePicker) {
            Dialog(onDismissRequest = { shouldShowDatePicker = false }) {
                BaseDatePickerLayout(
                    state = datePickerState,
                    onCancel = {
                        shouldShowDatePicker = false
                        inPickingProcess = false
                    },
                    onConfirm = {
                        shouldShowDatePicker = false
                        shouldShowTimePicker = true
                    }
                )
            }
        }

        if (shouldShowTimePicker) {
            Dialog(onDismissRequest = { shouldShowTimePicker = false }) {
                BaseTimePickerLayout(
                    state = timePickerState,
                    onCancel = {
                        shouldShowTimePicker = false
                        inPickingProcess = false
                    },
                    onConfirm = {
                        val selectedDateUtcMillis = datePickerState.selectedDateMillis
                        val selectedHour = timePickerState.hour
                        val selectedMinute = timePickerState.minute

                        selectedDateUtcMillis?.let {
                            val result = LocalDate.ofInstant(
                                Instant.ofEpochMilli(it),
                                ZoneId.ofOffset("", ZoneOffset.UTC)
                            ).atTime(selectedHour, selectedMinute)
                            onDateTimeChanged.invoke(result)
                        }

                        shouldShowTimePicker = false
                        inPickingProcess = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseDatePickerLayout(
    state: DatePickerState,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    val containerColor = MaterialTheme.colorScheme.surfaceContainer
    BaseDialogLayout(
        onCancel = onCancel,
        onConfirm = onConfirm,
        containerColor = containerColor,
    ) {
        DatePicker(
            state = state,
            colors = DatePickerDefaults.colors(containerColor = containerColor)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseTimePickerLayout(
    state: TimePickerState,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    val containerColor = MaterialTheme.colorScheme.surfaceContainer
    BaseDialogLayout(
        onCancel = onCancel,
        onConfirm = onConfirm,
        containerColor = containerColor,
    ) {
        TimePicker(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.spacer_xl)),
            state = state,
            colors = TimePickerDefaults.colors(containerColor = containerColor)
        )
    }
}

@Composable
private fun BaseDialogLayout(
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.size_l))
    Surface(
        shape = shape,
        color = containerColor,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.spacer_m)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
        ) {
            content()

            CompositionLocalProvider(LocalContentColor provides contentColorFor(containerColor)) {
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(dimensionResource(R.dimen.spacer_m)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
                ) {
                    TextButton(onClick = onCancel) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.common_cancel_label),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    TextButton(onClick = onConfirm) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.common_ok_label),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@MindExpensePreview
@Composable
private fun PreviewBaseDatePickerLayout() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            val state = rememberDatePickerState()
            BaseDatePickerLayout(state = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@MindExpensePreview
@Composable
private fun PreviewBaseTimePickerLayout() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            val state = rememberTimePickerState()
            BaseTimePickerLayout(state = state)
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewDateAndTimePicker() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
            DateAndTimePicker(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.spacer_l)),
                dateTime = dateTime,
                onDateTimeChanged = { dateTime = it },
            )
        }
    }
}