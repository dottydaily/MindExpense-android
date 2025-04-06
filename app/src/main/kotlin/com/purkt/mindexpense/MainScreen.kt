package com.purkt.mindexpense

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.purkt.mindexpense.features.expense.navigation.expenseGraph
import com.purkt.mindexpense.features.expense.navigation.navigateToExpenseAddScreen
import com.purkt.mindexpense.navigation.MainGraphRoute
import com.purkt.mindexpense.navigation.mainGraph

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = MainGraphRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400),
            )
        },
    ) {
        mainGraph(onOuterGoToExpenseAddScreen = { navController.navigateToExpenseAddScreen()})
        expenseGraph(onGoBackToPreviousPage = { navController.popBackStack() })
    }
}