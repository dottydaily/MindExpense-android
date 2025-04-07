package com.purkt.mindexpense.core.ui.common.composable

import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

/**
 * Ref : https://medium.com/@mittalkshitij20/adding-a-custom-scrollbar-to-a-column-in-jetpack-compose-9996c26f498f
 */
@Composable
fun Modifier.drawVerticalScrollBar(
    scrollState: ScrollState,
    scrollBarWidth: Dp = dimensionResource(R.dimen.size_s),
    scrollBarColor: Color = MaterialTheme.colorScheme.primary,
    endPadding: Dp = dimensionResource(R.dimen.spacer_xxs)
): Modifier {
    return drawWithContent {
        // Draw its content
        drawContent()

        // Viewport is the current box of this visible UI which modifier is applied.
        // For example, if column has height 300 (but content is more than that)
        // then the viewportHeight should be 300.
        val viewportHeight = this.size.height

        if (scrollState.maxValue == 0) return@drawWithContent // No need to draw scrollbar if there is no scrollable content

        // scrollState.maxValue is the maximum offset that this view can be scrolled in the corresponding axis.
        // if scrollState.maxValue is 200, then the total content's height should be 200 + viewPortHeight.
        val totalContentHeight = scrollState.maxValue.toFloat() + viewportHeight

        val currentScrollValue = scrollState.value.toFloat()
        val scrollBarHeight = (viewportHeight / totalContentHeight) * viewportHeight
        val scrollBarStartOffset = (currentScrollValue / totalContentHeight) * viewportHeight

        drawRoundRect(
            cornerRadius = CornerRadius(16f),
            color = scrollBarColor,
            topLeft = Offset(x = size.width - scrollBarWidth.toPx() - endPadding.toPx(), scrollBarStartOffset),
            size = Size(width = scrollBarWidth.toPx(), scrollBarHeight)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
private fun PreviewVerticalScrollBar() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .drawVerticalScrollBar(scrollState)
                    .verticalScroll(scrollState),
            ) {
                val dataList = listOf("Hello", "Hi", "Greeting", "Welcome", "Bye", "Goodbye")
                repeat(70) { order ->
                    Text(text = "$order.) ${dataList.random()}")
                }
            }
        }
    }
}