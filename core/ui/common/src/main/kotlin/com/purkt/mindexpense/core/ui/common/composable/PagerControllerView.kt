package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun PagerControllerView(
    modifier: Modifier = Modifier,
    currentPageIndex: Int,
    totalPage: Int,
    goToPreviousPage: () -> Unit = {},
    shouldShowPreviousButton: Boolean = true,
    goToNextPage: () -> Unit = {},
    shouldShowNextButton: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (shouldShowPreviousButton) {
            IconButton(onClick = goToPreviousPage) {
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
        } else Spacer(modifier = Modifier.size(dimensionResource(R.dimen.icon_button_min_size)))
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
            text = "${currentPageIndex + 1}/$totalPage",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (shouldShowNextButton) {
            IconButton(onClick = goToNextPage) {
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
        } else Spacer(modifier = Modifier.size(dimensionResource(R.dimen.icon_button_min_size)))
    }
}

@MindExpensePreviewOnlyLightDark
@Composable
private fun PreviewPagerControllerView() {
    MindExpenseTheme {
        Surface {
            PagerControllerView(
                modifier = Modifier.fillMaxWidth(),
                currentPageIndex = 1,
                totalPage = 3,
            )
        }
    }
}

@MindExpensePreviewOnlyLightDark
@Composable
private fun PreviewPagerControllerViewHidePrevious() {
    MindExpenseTheme {
        Surface {
            PagerControllerView(
                modifier = Modifier.fillMaxWidth(),
                currentPageIndex = 0,
                totalPage = 3,
                shouldShowPreviousButton = false,
            )
        }
    }
}

@MindExpensePreviewOnlyLightDark
@Composable
private fun PreviewPagerControllerViewHideNext() {
    MindExpenseTheme {
        Surface {
            PagerControllerView(
                modifier = Modifier.fillMaxWidth(),
                currentPageIndex = 2,
                totalPage = 3,
                shouldShowNextButton = false,
            )
        }
    }
}