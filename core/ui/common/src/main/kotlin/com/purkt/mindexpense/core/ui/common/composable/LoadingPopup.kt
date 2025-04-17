package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun LoadingPopup(isShowing: Boolean) {
    if (isShowing) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = {},
            properties = PopupProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewLoadingPopup() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoadingPopup(true)
        }
    }
}