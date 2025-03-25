package com.purkt.mindexpense.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
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
import com.purkt.mindexpense.data.expense.model.Expense
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()

    BaseHomeScreen(expenses = expenses)
}

@Preview
@Composable
private fun BaseHomeScreen(
    expenses: List<Expense> = emptyList(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    text = "This is Home screen",
                )

                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = expenses.isNotEmpty(),
                ) {
                    Column(modifier = Modifier.defaultMinSize(minHeight = 300.dp)) {
                        expenses.forEach {
                            Text(text = it.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBaseHomeScreenWithData() {
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
    val mockExpenses = listOf(
        Expense(
            localId = "1",
            ownerUserId = "1",
            title = "Dinner",
            receiver = "KFC",
            note = "Dinner with friends",
            amount = 100.0,
            imageUrl = "",
            paidAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
            createdAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
            updatedAt = LocalDateTime.parse("2025-03-24T00:00:00Z", pattern),
        ),
        Expense(
            localId = "2",
            ownerUserId = "1",
            title = "Coffee",
            receiver = "Starbucks",
            note = "Coffee before work",
            amount = 80.0,
            imageUrl = "",
            paidAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
            createdAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
            updatedAt = LocalDateTime.parse("2025-03-24T10:00:00Z", pattern),
        ),
        Expense(
            localId = "3",
            ownerUserId = "1",
            title = "Lunch",
            receiver = "McDonald",
            note = "Lunch fast food",
            amount = 150.0,
            imageUrl = "",
            paidAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
            createdAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
            updatedAt = LocalDateTime.parse("2025-03-25T05:12:00Z", pattern),
        ),
        Expense(
            localId = "4",
            ownerUserId = "1",
            title = "Car fuel",
            receiver = "Shell",
            note = "Fill car tank",
            amount = 500.0,
            imageUrl = "",
            paidAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern),
            createdAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern),
            updatedAt = LocalDateTime.parse("2025-03-26T12:40:00Z", pattern)
        )
    )
    BaseHomeScreen(expenses = mockExpenses)
}