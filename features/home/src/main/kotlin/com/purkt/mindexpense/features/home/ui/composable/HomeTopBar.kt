package com.purkt.mindexpense.features.home.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.purkt.mindexpense.core.data.common.formatCurrencyOrNull
import com.purkt.mindexpense.core.ui.common.R

@Composable
internal fun HomeTopBar(
    modifier: Modifier = Modifier,
    totalAmount: Double = 0.0,
    titleColor: Color = LocalContentColor.current,
    amountColor: Color = LocalContentColor.current,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(id = R.string.home_top_bar_label),
            color = titleColor,
            fontWeight = FontWeight.Bold,
        )
        Text(
            style = MaterialTheme.typography.displayMedium,
            text = totalAmount.formatCurrencyOrNull() ?: "-",
            color = amountColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "th")
@Composable
private fun PreviewHomeTopBar(
    @PreviewParameter(PreviewTotalOutcomeProvider::class) totalOutcome: Double,
) {
    CompositionLocalProvider(LocalContentColor provides Color.Red) {
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