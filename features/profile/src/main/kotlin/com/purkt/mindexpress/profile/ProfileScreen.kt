package com.purkt.mindexpress.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun ProfileScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding -> 
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = "This is Profile screen",
            )
        }
    }
}