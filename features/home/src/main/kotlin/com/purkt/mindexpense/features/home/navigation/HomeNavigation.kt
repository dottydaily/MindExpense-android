package com.purkt.mindexpense.features.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpense.features.home.ui.HomeScreen
import kotlinx.serialization.Serializable

/**
 * Route for Home screen.
 */
@Serializable
internal data object HomeRoute

/**
 * Route for Home graph.
 */
@Serializable
data object HomeGraphRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = HomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.homeGraph(
    onOuterGoToExpenseAddScreen: () -> Unit,
    onOuterGoToExpenseEditScreen: (id: String, hasBeenSynced: Boolean) -> Unit,
) {
    navigation<HomeGraphRoute>(startDestination = HomeRoute) {
        composable<HomeRoute>{
            HomeScreen(
                onOuterGoToExpenseAddScreen = onOuterGoToExpenseAddScreen,
                onOuterGoToExpenseEditScreen = {
                    onOuterGoToExpenseEditScreen.invoke(
                        it.currentId.toString(),
                        it.hasBeenSyncedAtLeastOnce()
                    )
                },
            )
        }
    }
}