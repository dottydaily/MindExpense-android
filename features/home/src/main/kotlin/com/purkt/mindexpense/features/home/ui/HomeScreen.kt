package com.purkt.mindexpense.features.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.features.home.ui.composable.ExpenseItem
import com.purkt.mindexpense.features.home.ui.composable.HomeTopBar
import com.purkt.mindexpense.features.home.ui.composable.mockExpenses
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
        shouldShowFabButton = viewModel.currentUserId != null,
        onAddExpense = onAddExpense,
        onDeleteExpense = onDeleteExpense,
    )
}

@Composable
private fun BaseHomeScreen(
    expenses: List<Expense> = emptyList(),
    shouldShowFabButton: Boolean = true,
    onAddExpense: () -> Unit = {},
    onDeleteExpense: (Expense) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            val contentColor = contentColorFor(backgroundColor)
            Box(modifier = Modifier.fillMaxWidth()) {
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    HomeTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = backgroundColor)
                            .statusBarsPadding()
                            .displayCutoutPadding()
                            .padding(16.dp),
                        totalAmount = expenses.sumOf { it.amount },
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (shouldShowFabButton) {
                FloatingActionButton(
                    onClick = onAddExpense,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                    )
                }
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
            .exclude(WindowInsets.navigationBars) // Handled by Parent's Scaffold instead.
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items = expenses, key = { it.uniqueId }) { expense ->
                ExpenseItem(
                    modifier = Modifier.fillMaxWidth(),
                    data = expense,
                    onDeleteItem = onDeleteExpense,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBaseHomeScreenWithoutData() {
    MindExpenseTheme {
        BaseHomeScreen()
    }
}

@Preview
@Composable
private fun PreviewBaseHomeScreenWithData() {
    MindExpenseTheme {
        BaseHomeScreen(expenses = mockExpenses)
    }
}