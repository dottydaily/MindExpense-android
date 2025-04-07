package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: @Composable () -> Unit,
    padding: PaddingValues = PaddingValues(
        horizontal = dimensionResource(R.dimen.spacer_l),
        vertical = dimensionResource(R.dimen.spacer_s),
    )
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.size_l)))
            .clickable(onClick = onClick)
            .then(modifier)
            .padding(padding),
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.spacer_s),
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = text,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewIconTextButton() {
    MindExpenseTheme {
        Surface {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                IconTextButton(
                    onClick = {},
                    text = stringResource(id = R.string.expense_delete),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete icon",
                        )
                    }
                )
            }
        }
    }
}