package com.purkt.mindexpense.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.purkt.mindexpense.InnerNavHostScreen
import kotlinx.serialization.Serializable

@Serializable
internal data object MainRoute

@Serializable
internal data object MainGraphRoute

fun NavGraphBuilder.mainGraph(onOuterGoToExpenseAddScreen: () -> Unit) {
    navigation<MainGraphRoute>(startDestination = MainRoute) {
        composable<MainRoute> {
            InnerNavHostScreen(onOuterGoToExpenseAddScreen = onOuterGoToExpenseAddScreen)
        }
    }
}