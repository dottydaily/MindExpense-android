package com.purkt.mindexpense

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.purkt.mindexpense.home.navigation.HomeGraphRoute
import com.purkt.mindexpense.home.navigation.homeGraph
import com.purkt.mindexpress.profile.navigation.ProfileGraphRoute
import com.purkt.mindexpress.profile.navigation.profileGraph

@Preview
@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController = navController)
        }
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
            homeGraph()
            profileGraph()
        }
    }
}

@Composable
private fun BottomBar(navController: NavController) {
    val menuBarItems = listOf(
        MenuBarItemInfo(graphRoute = HomeGraphRoute, name = "Home", icon = Icons.Default.Home),
        MenuBarItemInfo(graphRoute = ProfileGraphRoute, name = "Profile", icon = Icons.Default.Person),
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        menuBarItems.forEach { info ->
            NavigationBarItem(
                selected = currentDestination?.isSelectedRoute(info = info) == true,
                onClick = {
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
                icon = {
                    Icon(
                        imageVector = info.icon,
                        contentDescription = "Bottom bar menu item of ${info.name}"
                    )
                },
                label = {
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        text = info.name,
                    )
                }
            )
        }
    }
}

private fun<Route: Any> NavDestination.isSelectedRoute(info: MenuBarItemInfo<Route>): Boolean {
    return hierarchy.any { it.hasRoute(info.graphRoute::class) }
}

private data class MenuBarItemInfo<Route: Any>(
    val graphRoute: Route,
    val name: String,
    val icon: ImageVector,
)