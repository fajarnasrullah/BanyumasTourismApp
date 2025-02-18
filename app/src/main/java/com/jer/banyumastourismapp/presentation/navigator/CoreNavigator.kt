package com.jer.banyumastourismapp.presentation.navigator

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.presentation.component.Destination
import com.jer.banyumastourismapp.presentation.destination.DestinationListScreen
import com.jer.banyumastourismapp.presentation.detailDestination
import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestination
import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestinationScreen
import com.jer.banyumastourismapp.presentation.detaildestination.Facility
import com.jer.banyumastourismapp.presentation.home.HomeScreen
import com.jer.banyumastourismapp.presentation.home.User
import com.jer.banyumastourismapp.presentation.itinerary
import com.jer.banyumastourismapp.presentation.itinerary.City
import com.jer.banyumastourismapp.presentation.itinerary.Itinerary
import com.jer.banyumastourismapp.presentation.itinerary.ItineraryScreen
import com.jer.banyumastourismapp.presentation.itinerary.Plan
import com.jer.banyumastourismapp.presentation.itinerary.PlanCategory
import com.jer.banyumastourismapp.presentation.listCardPlan
import com.jer.banyumastourismapp.presentation.listDestination
import com.jer.banyumastourismapp.presentation.listSosmed
import com.jer.banyumastourismapp.presentation.navgraph.Route
import com.jer.banyumastourismapp.presentation.orders.OrdersFormScreen
import com.jer.banyumastourismapp.presentation.plan
import com.jer.banyumastourismapp.presentation.profile.ProfileScreen
import com.jer.banyumastourismapp.presentation.profile.bookmark.BookmarkState
import com.jer.banyumastourismapp.presentation.sosmed.Sosmed
import com.jer.banyumastourismapp.presentation.sosmed.SosmedListScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun CoreNavigator() {




    val listBottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(
                label = "Home",
                icon = R.drawable.homeicon
            ),
            BottomNavigationItem(
                label = "Destination",
                icon = R.drawable.destinationlisticon
            ),
            BottomNavigationItem(
                label = "Story",
                icon = R.drawable.sosmedicon
            ),
            BottomNavigationItem(
                label = "Ticket",
                icon = R.drawable.ticketicon
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = R.drawable.profileicon
            ),
        )
    }



    val hazeState = remember { HazeState() }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.DestinationListScreen.route -> 1
            Route.SosmedListScreen.route -> 2
            Route.TicketHistoryScreen.route -> 3
            Route.ProfileScreen.route -> 4
            else -> 0
        }

    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
        backStackState?.destination?.route == Route.DestinationListScreen.route ||
        backStackState?.destination?.route == Route.SosmedListScreen.route ||
        backStackState?.destination?.route == Route.TicketHistoryScreen.route ||
        backStackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                CoreBottomNavigation(
                    items = listBottomNavigationItem,
                    selected = selectedItem,
                    hazeState = hazeState,
                    onItemClick = {index ->
                        when (index){
                            0 -> navigateToTab(navController, Route.HomeScreen.route)
                            1 -> navigateToTab(navController, Route.DestinationListScreen.route)
                            2 -> navigateToTab(navController, Route.SosmedListScreen.route)
                            3 -> navigateToTab(navController, Route.TicketHistoryScreen.route)
                            4 -> navigateToTab(navController, Route.ProfileScreen.route)

                        }

                    }
                )
            }
        }
    ) { paddingValues ->
        val bottomBarPadding = paddingValues.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = MaterialTheme.colorScheme.background.copy(alpha = .7f),
                    blurRadius = 50.dp,
                )
//                .blur(50.dp)
//            modifier = Modifier.padding(bottom = bottomBarPadding)
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(
                    user = User("Fajar"),
                    destination = listDestination,
                    navigateToDetail = { navController.navigate(Route.DetailDestinationScreen.route) },
                    navigateToItinerary = { navController.navigate(Route.ItineraryScreen.route) }
                )
            }
            composable(Route.DestinationListScreen.route) {
                DestinationListScreen(
                    user = User("Fajar"),
                    destination = listDestination,
                    navigateToDetail = { navController.navigate(Route.DetailDestinationScreen.route) }
                )
            }

            composable(Route.SosmedListScreen.route) {
                SosmedListScreen(
                    listSosmed = listSosmed,
                    navigateToDetail = { navController.navigate(Route.DetailSosmedScreen.route) }
                )
            }

            composable(Route.TicketHistoryScreen.route) {
            }

            composable(Route.ProfileScreen.route) {
                ProfileScreen(
                    user = User(name = "Fajar Nasrullah", desc = "akwokaowkowka"),
                    state = BookmarkState(),
                    navigateToDetail = { /*TODO*/ })
            }

            composable(Route.DetailDestinationScreen.route) {
                DetailDestinationScreen(
                    detailDestination = detailDestination,
                    navToOrders = {navController.navigate(Route.OrdersFormScreen.route)},
                    navBack = {navController.navigateUp()}
                )
            }

            composable(Route.DetailSosmedScreen.route) {

            }

            composable(Route.ItineraryScreen.route) {
                ItineraryScreen(
                    user = User("Fajar"),
                    itinerary = itinerary,
                    plan = plan,
                    onClick = {},
                    navBack = {navController.navigateUp()}
                )
            }

            composable(Route.OrdersFormScreen.route) {
                OrdersFormScreen (
                    navBack = {navController.navigateUp()}
                )
            }
        }

    }



}

fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { fromHomeScreen ->
            popUpTo(fromHomeScreen) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

//private fun navigateToDetail(navController: NavController, article: Article) {
//    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
//    navController.navigate(Route.DetailsScreen.route)
//}




