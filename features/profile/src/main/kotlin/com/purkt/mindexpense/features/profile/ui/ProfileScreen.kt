package com.purkt.mindexpense.features.profile.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.purkt.mindexpense.core.data.users.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel()) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()

    BaseProfileScreen(user = currentUser)
}

@Preview
@Composable
private fun BaseProfileScreen(
    user: User? = null
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge,
                    text = "This is Profile screen",
                )

                AnimatedContent(
                    modifier = Modifier.fillMaxWidth(),
                    targetState = user,
                ) { targetUser ->
                    if (targetUser == null) {
                        Text(text = "User not found")
                    } else {
                        Text(text = targetUser.toString())
                    }
                }
            }
        }
    }
}