package com.purkt.mindexpense.features.expense.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.purkt.mindexpense.features.expense.ui.add.ExpenseAddScreen
import com.purkt.mindexpense.features.expense.ui.edit.ExpenseEditScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Route for [ExpenseAddScreen] screen.
 */
@Serializable
internal data object ExpenseAddRoute

/**
 * Route for [ExpenseEditScreen] screen.
 */
@Serializable
internal data class ExpenseEditRoute(
    val expenseId: String,
    val hasBeenSynced: Boolean,
)

/**
 * Route for Expense graph.
 */
@Serializable
data object ExpenseGraph

fun NavController.navigateToExpenseAddScreen(navOptions: NavOptions? = null) {
    navigate(route = ExpenseAddRoute, navOptions = navOptions)
}

fun NavController.navigateToExpenseEditScreen(
    expenseId: String,
    hasBeenSynced: Boolean,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = ExpenseEditRoute(expenseId = expenseId, hasBeenSynced = hasBeenSynced),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.expenseGraph(onGoBackToPreviousPage: () -> Unit) {
    navigation<ExpenseGraph>(startDestination = ExpenseAddRoute) {
        composable<ExpenseAddRoute>{
            ExpenseAddScreen(onGoBackToPreviousPage = onGoBackToPreviousPage)
        }
        composable<ExpenseEditRoute>{
            val route = it.toRoute<ExpenseEditRoute>()
            val expenseId = route.expenseId
            val hasBeenSynced = route.hasBeenSynced
            ExpenseEditScreen(
                onGoBackToPreviousPage = onGoBackToPreviousPage,
                viewModel = koinViewModel(
                    parameters = { parametersOf(expenseId, hasBeenSynced) },
                ),
            )
        }
    }
}