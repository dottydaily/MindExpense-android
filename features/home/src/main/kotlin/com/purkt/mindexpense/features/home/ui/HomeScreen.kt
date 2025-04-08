package com.purkt.mindexpense.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.composable.rememberListenerNullableParams
import com.purkt.mindexpense.core.ui.common.composable.rememberListenerParams
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.core.ui.expense.composable.ExpenseItem
import com.purkt.mindexpense.features.home.ui.composable.ConfirmDeleteDialog
import com.purkt.mindexpense.features.home.ui.composable.HomeTopBar
import com.purkt.mindexpense.features.home.ui.composable.mockExpenses
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(
    onOuterGoToExpenseAddScreen: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val setConfirmDeleteDialog: (Expense?) -> Unit = rememberListenerNullableParams {
        viewModel.setConfirmDeleteDialog(pendingExpense = it)
    }
    val onDeleteExpense: (Expense) -> Unit = rememberListenerParams { viewModel.deleteExpense(it) }

    BaseHomeScreen(
        expenses = expenses,
        shouldShowFabButton = viewModel.currentUserId != null,
        onAddExpense = onOuterGoToExpenseAddScreen,
        expenseToBeDeleted = viewModel.expenseToDeleted,
        onSetConfirmDeleteDialog = setConfirmDeleteDialog,
        onDeleteExpense = onDeleteExpense,
    )
}

@Composable
private fun BaseHomeScreen(
    expenses: List<Expense> = emptyList(),
    shouldShowFabButton: Boolean = true,
    onAddExpense: () -> Unit = {},
    expenseToBeDeleted: Expense? = null,
    onSetConfirmDeleteDialog: (Expense?) -> Unit = {},
    onDeleteExpense: (Expense) -> Unit = {},
) {
    ConfirmDeleteDialog(
        isShowing = expenseToBeDeleted != null,
        message = expenseToBeDeleted?.title.orEmpty(),
        onDismiss = {
            expenseToBeDeleted?.let {
                onSetConfirmDeleteDialog.invoke(null)
            }
        },
        onClickConfirmButton = {
            expenseToBeDeleted?.let {
                onDeleteExpense.invoke(it)
                onSetConfirmDeleteDialog.invoke(null)
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val backgroundColor = MaterialTheme.colorScheme.surface
            val contentColor = contentColorFor(backgroundColor)
            Box(modifier = Modifier.fillMaxWidth()) {
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    HomeTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = backgroundColor)
                            .statusBarsPadding()
                            .displayCutoutPadding()
                            .padding(
                                horizontal = dimensionResource(R.dimen.spacer_l),
                                vertical = dimensionResource(R.dimen.spacer_m),
                            ),
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
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacer_m)),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_s)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                        )
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = stringResource(id = R.string.home_fab_label),
                            fontWeight = FontWeight.Bold
                        )
                    }
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
            contentPadding = PaddingValues(dimensionResource(R.dimen.spacer_l)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
        ) {
            items(items = expenses, key = { it.uniqueId }) { expense ->
                ExpenseItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    contentPadding = PaddingValues(dimensionResource(R.dimen.spacer_l)),
                    data = expense,
                    onClickDeleteButton = { onSetConfirmDeleteDialog.invoke(expense) },
                )
            }
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseHomeScreenWithoutData() {
    MindExpenseTheme {
        BaseHomeScreen()
    }
}

@MindExpensePreview
@Composable
private fun PreviewBaseHomeScreenWithData() {
    MindExpenseTheme {
        BaseHomeScreen(expenses = mockExpenses)
    }
}