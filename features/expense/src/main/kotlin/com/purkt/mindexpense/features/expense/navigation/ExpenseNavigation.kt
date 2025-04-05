package com.purkt.mindexpense.features.expense.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpense.features.expense.ui.add.ExpenseAddScreen
import kotlinx.serialization.Serializable

/**
 * Route for ExpenseAddRoute screen.
 */
@Serializable
internal data object ExpenseAddRoute

/**
 * Route for Expense graph.
 */
@Serializable
data object ExpenseGraph

fun NavController.navigateToExpenseAddScreen(navOptions: NavOptions? = null) {
    navigate(route = ExpenseAddRoute, navOptions = navOptions)
}

fun NavGraphBuilder.expenseGraph(onGoBackToPreviousPage: () -> Unit) {
    navigation<ExpenseGraph>(startDestination = ExpenseAddRoute) {
        composable<ExpenseAddRoute>{
            ExpenseAddScreen(onGoBackToPreviousPage = onGoBackToPreviousPage)
        }
    }
}