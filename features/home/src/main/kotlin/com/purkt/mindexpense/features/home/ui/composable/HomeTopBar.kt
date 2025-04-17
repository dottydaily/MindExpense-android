package com.purkt.mindexpense.features.home.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.data.common.toFormattedStringOrNull
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import java.time.YearMonth

@Composable
internal fun HomeTopBar(
    modifier: Modifier = Modifier,
    totalAmount: Double = 0.0,
    titleColor: Color = LocalContentColor.current,
    amountColor: Color = LocalContentColor.current,
    currentYearMonth: YearMonth = YearMonth.now(),
    goToPreviousMonth: () -> Unit = {},
    goToNextMonth: () -> Unit = {},
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_m))
    ) {
        val totalAmountText by remember(key1 = totalAmount) {
            mutableStateOf(totalAmount.formatCurrencyOrNull() ?: "-")
        }

        Column {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(id = R.string.home_top_bar_label),
                color = titleColor,
                fontWeight = FontWeight.Bold,
            )
            Text(
                style = MaterialTheme.typography.displayMedium,
                text = totalAmountText,
                color = amountColor,
                fontWeight = FontWeight.Bold,
            )
        }

        MonthControllerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            currentYearMonth = currentYearMonth,
            goToPreviousMonth = goToPreviousMonth,
            goToNextMonth = goToNextMonth,
        )
    }
}

@Composable
fun MonthControllerView(
    modifier: Modifier = Modifier,
    currentYearMonth: YearMonth = YearMonth.now(),
    goToPreviousMonth: () -> Unit = {},
    goToNextMonth: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_xl)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val monthText by remember(key1 = currentYearMonth) {
            mutableStateOf(currentYearMonth.toFormattedStringOrNull() ?: "-")
        }

        IconButton(onClick = goToPreviousMonth) {
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
        Box(
            modifier = Modifier
                .height(dimensionResource(R.dimen.size_xl))
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    horizontal = dimensionResource(R.dimen.spacer_m),
                    vertical = dimensionResource(R.dimen.spacer_xs),
                )
        ) {
            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
                    .align(Alignment.Center),
                style = MaterialTheme.typography.titleSmall,
                text = monthText,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        IconButton(onClick = goToNextMonth) {
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
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "th")
@Composable
private fun PreviewHomeTopBar(
    @PreviewParameter(PreviewTotalOutcomeProvider::class) totalOutcome: Double,
) {
    MindExpenseTheme {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            totalAmount = totalOutcome,
        )
    }
}

private class PreviewTotalOutcomeProvider: PreviewParameterProvider<Double> {
    override val values: Sequence<Double>
        get() = sequenceOf(
            0.0,
            mockExpenses.sumOf { it.amount },
            12_344.07,
        )
}