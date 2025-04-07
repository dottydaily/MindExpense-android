package com.purkt.mindexpense.features.expense.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.purkt.mindexpense.core.ui.common.R

@Composable
internal fun ButtonBarLayout(
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