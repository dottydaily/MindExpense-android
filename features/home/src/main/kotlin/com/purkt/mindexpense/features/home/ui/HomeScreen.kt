package com.purkt.mindexpense.features.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.purkt.mindexpense.core.data.expense.model.Expense
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.LoadingDialog
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.core.ui.expense.composable.ExpenseItem
import com.purkt.mindexpense.features.home.ui.composable.ConfirmDeleteDialog
import com.purkt.mindexpense.features.home.ui.composable.HomeTopBar
import com.purkt.mindexpense.features.home.ui.composable.mockExpenses
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth

@Composable
internal fun HomeScreen(
    onOuterGoToExpenseAddScreen: () -> Unit,
    onOuterGoToExpenseEditScreen: (Expense) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val expensesByYearMonth by viewModel.expensesByYearMonth.collectAsStateWithLifecycle()
    BaseHomeScreen(
        expensesByYearMonth = expensesByYearMonth,
        isLoading = viewModel.isLoading,
        shouldShowFabButton = expensesByYearMonth != null,
        goToPreviousMonth = viewModel::changeToPreviousMonth,
        goToNextMonth = viewModel::changeToNextMonth,
        onClickAddExpenseButton = onOuterGoToExpenseAddScreen,
        onClickEditExpenseItem = onOuterGoToExpenseEditScreen,
        expenseToBeDeleted = viewModel.expenseToDeleted,
        onSetConfirmDeleteDialog = viewModel::setConfirmDeleteDialog,
        onClickDeleteExpenseItem = viewModel::deleteExpense,
    )
}

@Composable
private fun BaseHomeScreen(
    expensesByYearMonth: ExpensesByYearMonth? = ExpensesByYearMonth(),
    isLoading: Boolean = false,
    shouldShowFabButton: Boolean = true,
    goToPreviousMonth: () -> Unit = {},
    goToNextMonth: () -> Unit = {},
    onClickAddExpenseButton: () -> Unit = {},
    onClickEditExpenseItem: (target: Expense) -> Unit = {},
    expenseToBeDeleted: Expense? = null,
    onSetConfirmDeleteDialog: (Expense?) -> Unit = {},
    onClickDeleteExpenseItem: (Expense) -> Unit = {},
) {
    val expenses = expensesByYearMonth?.expenses
    val currentYearMonth = expensesByYearMonth?.yearMonth
    var contentAnimatedDirection: AnimatedContentTransitionScope.SlideDirection by remember {
        mutableStateOf(AnimatedContentTransitionScope.SlideDirection.Start)
    }

    LoadingDialog(isShowing = isLoading)

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
                onClickDeleteExpenseItem.invoke(it)
                onSetConfirmDeleteDialog.invoke(null)
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val backgroundColor = MaterialTheme.colorScheme.surface
            val contentColor = contentColorFor(backgroundColor)
            val totalAmount: Double? by remember(key1 = expenses) {
                mutableStateOf(expenses?.sumOf { it.amount })
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxWidth(),
                        visible = totalAmount != null && currentYearMonth != null,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        HomeTopBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = backgroundColor)
                                .statusBarsPadding()
                                .displayCutoutPadding()
                                .padding(
                                    top = dimensionResource(R.dimen.spacer_m),
                                    start = dimensionResource(R.dimen.spacer_l),
                                    end = dimensionResource(R.dimen.spacer_l),
                                    bottom = dimensionResource(R.dimen.spacer_s),
                                ),
                            totalAmount = totalAmount!!,
                            currentYearMonth = currentYearMonth!!,
                            goToPreviousMonth = {
                                contentAnimatedDirection = AnimatedContentTransitionScope.SlideDirection.End
                                goToPreviousMonth.invoke()
                            },
                            goToNextMonth = {
                                contentAnimatedDirection = AnimatedContentTransitionScope.SlideDirection.Start
                                goToNextMonth.invoke()
                            },
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (shouldShowFabButton) {
                FloatingActionButton(
                    onClick = onClickAddExpenseButton,
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
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            visible = expensesByYearMonth != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = expensesByYearMonth,
                transitionSpec = {
                    val enter = fadeIn(tween(200)) + slideIntoContainer(
                        towards = contentAnimatedDirection,
                        animationSpec = tween(400),
                    )
                    val exit = fadeOut(tween(200)) + slideOutOfContainer(
                        towards = contentAnimatedDirection,
                        animationSpec = tween(400),
                    )
                    enter.togetherWith(exit)
                },
            ) { target ->
                if (target == null || target.expenses.isEmpty()) {
                    EmptyContent()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(dimensionResource(R.dimen.spacer_l)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_l)),
                    ) {
                        items(items = target.expenses, key = { it.uniqueId }) { expense ->
                            ExpenseItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(),
                                contentPadding = PaddingValues(dimensionResource(R.dimen.spacer_l)),
                                data = expense,
                                onClickEditButton = { onClickEditExpenseItem.invoke(expense) },
                                onClickDeleteButton = { onSetConfirmDeleteDialog.invoke(expense) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier
            .padding(top = 120.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(R.dimen.spacer_l)),
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            imageVector = Icons.Default.Info,
            contentDescription = "No expense icon",
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.home_no_expense_message),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
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
        BaseHomeScreen(expensesByYearMonth = ExpensesByYearMonth(expenses = mockExpenses))
    }
}

@Stable
internal data class ExpensesByYearMonth(
    val expenses: List<Expense> = emptyList(),
    val yearMonth: YearMonth = YearMonth.now(),
)