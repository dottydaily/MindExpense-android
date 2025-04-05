package com.purkt.mindexpense.features.expense.ui.add

import androidx.compose.runtime.Composable
import com.purkt.mindexpense.features.expense.ui.composable.BaseExpenseScreen
import com.purkt.mindexpense.features.expense.ui.composable.ExpenseScreenType

@Composable
internal fun ExpenseAddScreen(onGoBackToPreviousPage: () -> Unit) {
    BaseExpenseScreen(
        mode = ExpenseScreenType.ADD,
        onGoBack = onGoBackToPreviousPage,
    )
}