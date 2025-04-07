package com.purkt.mindexpense.features.expense.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.DateAndTimePicker
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreviewAllScales
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import java.time.LocalDateTime

@Composable
internal fun PaidAtDateTimePicker(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
    ),
    paidAt: LocalDateTime,
    onPaidAtChanged: (LocalDateTime) -> Unit,
) {
    DateAndTimePicker(
        modifier = modifier
            .defaultMinSize(minWidth = 380.dp)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.size_m)))
            .background(containerColor)
            .padding(dimensionResource(R.dimen.spacer_m)),
        buttonColor = buttonColor,
        dateTime = paidAt,
        onDateTimeChanged = onPaidAtChanged,
    )
}

@MindExpensePreviewAllScales
@MindExpensePreview
@Composable
private fun PreviewPaidAtDateTimePicker() {
    MindExpenseTheme {
        Surface {
            PaidAtDateTimePicker(
                paidAt = LocalDateTime.now(),
                onPaidAtChanged = {},
            )
        }
    }
}
