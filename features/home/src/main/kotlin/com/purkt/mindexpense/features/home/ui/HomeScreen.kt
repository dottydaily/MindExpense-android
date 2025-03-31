package com.purkt.mindexpense.features.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.purkt.mindexpense.core.data.common.toLocalDateTimeOrThrowError
import com.purkt.mindexpense.core.data.expense.model.Expense
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val onAddExpense = remember {
        { viewModel.createRandomExpense() }
    }
    val onDeleteExpense: (Expense) -> Unit = remember { { viewModel.deleteExpense(it) } }

    BaseHomeScreen(
        expenses = expenses,
        shouldShowAddRandomExpenseButton = viewModel.currentUserId != null,
        onAddExpense = onAddExpense,
        onDeleteExpense = onDeleteExpense,
    )
}

@Preview
@Composable
private fun BaseHomeScreen(
    expenses: List<Expense> = emptyList(),
    shouldShowAddRandomExpenseButton: Boolean = true,
    onAddExpense: () -> Unit = {},
    onDeleteExpense: (Expense) -> Unit = {},
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
                    .align(alignment = Alignment.Center)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleLarge,
                    text = "This is Home screen",
                )

                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    visible = expenses.isNotEmpty(),
                ) {
                    Column(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 300.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        expenses.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(color = MaterialTheme.colorScheme.primary),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(16.dp),
                                    text = item.toString(),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                IconButton(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.onError),
                                    onClick = { onDeleteExpense.invoke(item) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete expense icon",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = shouldShowAddRandomExpenseButton,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onAddExpense,
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "Add a random expense",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBaseHomeScreenWithData() {
    BaseHomeScreen(expenses = mockExpenses)
}

private val mockExpenses = listOf(
    Expense(
        localId = 1,
        ownerUserId = 1,
        title = "Dinner",
        receiver = "KFC",
        note = "Dinner with friends",
        amount = 100.0,
        imageUrl = "",
        paidAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 2,
        ownerUserId = 1,
        title = "Coffee",
        receiver = "Starbucks",
        note = "Coffee before work",
        amount = 80.0,
        imageUrl = "",
        paidAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 3,
        ownerUserId = 1,
        title = "Lunch",
        receiver = "McDonald",
        note = "Lunch fast food",
        amount = 150.0,
        imageUrl = "",
        paidAt = "2025-03-25T05:12:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-25T05:12:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-25T05:12:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 4,
        ownerUserId = 1,
        title = "Car fuel",
        receiver = "Shell",
        note = "Fill car tank",
        amount = 500.0,
        imageUrl = "",
        paidAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError()
    )
)