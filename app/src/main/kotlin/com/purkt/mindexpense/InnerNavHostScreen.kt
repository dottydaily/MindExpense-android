package com.purkt.mindexpense

import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.composable.MindExpensePreview
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme
import com.purkt.mindexpense.features.home.navigation.HomeGraphRoute
import com.purkt.mindexpense.features.home.navigation.homeGraph
import com.purkt.mindexpense.features.profile.navigation.ProfileGraphRoute
import com.purkt.mindexpense.features.profile.navigation.profileGraph

@Composable
internal fun InnerNavHostScreen(onOuterGoToExpenseAddScreen: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            BottomBar(
                onCheckSelected = { currentDestination?.isSelectedRoute(info = it) == true },
                onClickMenu = { info ->
                    navController.navigate(route = info.graphRoute) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(id = navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same item
                        launchSingleTop = true

                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
            .exclude(WindowInsets.statusBars) // Handled in Child's Scaffold instead.
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = HomeGraphRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            homeGraph(onOuterGoToExpenseAddScreen = onOuterGoToExpenseAddScreen)
            profileGraph()
        }
    }
}

@Composable
private fun BottomBar(
    onCheckSelected: (MenuBarItemInfo) -> Boolean = { false },
    onClickMenu: (MenuBarItemInfo) -> Unit = {}
) {
    val menuBarItems = listOf(
        MenuBarItemInfo.Home,
        MenuBarItemInfo.Profile,
    )

    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = contentColorFor(containerColor)

    NavigationBar(containerColor = containerColor) {
        menuBarItems.forEach { info ->
            NavigationBarItem(
                selected = onCheckSelected.invoke(info),
                onClick = { onClickMenu.invoke(info) },
                icon = {
                    Icon(
                        imageVector = info.icon,
                        contentDescription = "Bottom bar menu item of ${stringResource(id = info.nameResId)}"
                    )
                },
                label = {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        text = stringResource(id = info.nameResId),
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = contentColor,
                    selectedTextColor = contentColor,
                    unselectedTextColor = contentColor,
                    selectedIconColor = containerColor,
                    unselectedIconColor = contentColor,
                )
            )
        }
    }
}

@MindExpensePreview
@Composable
private fun PreviewBottomBar() {
    MindExpenseTheme {
        var selected: MenuBarItemInfo by rememberSaveable { mutableStateOf(MenuBarItemInfo.Home) }

        BottomBar(
            onCheckSelected = { selected == it },
            onClickMenu = { selected = it }
        )
    }
}

private fun NavDestination.isSelectedRoute(info: MenuBarItemInfo): Boolean {
    return hierarchy.any { it.hasRoute(info.graphRoute::class) }
}

private sealed class MenuBarItemInfo(val graphRoute: Any, @StringRes val nameResId: Int, val icon: ImageVector) {
    data object Home: MenuBarItemInfo(
        graphRoute = HomeGraphRoute,
        nameResId = R.string.bottom_bar_home_label,
        icon = Icons.Default.Home
    )

    data object Profile: MenuBarItemInfo(
        graphRoute = ProfileGraphRoute,
        nameResId = R.string.bottom_bar_profile_label,
        icon = Icons.Default.Person
    )
}