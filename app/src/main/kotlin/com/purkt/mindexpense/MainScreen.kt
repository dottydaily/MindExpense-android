package com.purkt.mindexpense

import androidx.compose.animation.AnimatedContentTransitionScope
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
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        mainGraph(onOuterGoToExpenseAddScreen = { navController.navigateToExpenseAddScreen()})
        expenseGraph(onGoBackToPreviousPage = { navController.popBackStack() })
    }
}