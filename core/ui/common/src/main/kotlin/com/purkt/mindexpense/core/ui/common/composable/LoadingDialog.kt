package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun LoadingDialog(isShowing: Boolean) {
    if (isShowing) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
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
private fun PreviewLoadingDialog() {
    MindExpenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoadingDialog(true)
        }
    }
}