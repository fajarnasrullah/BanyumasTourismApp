package com.jer.banyumastourismapp.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.usecase.tourism.GetCurrentUser
import com.jer.banyumastourismapp.presentation.auth.AuthViewModel
import com.jer.banyumastourismapp.presentation.auth.login.LoginScreen
import com.jer.banyumastourismapp.presentation.destination.DestinationListScreen
import com.jer.banyumastourismapp.presentation.detailDestination
import com.jer.banyumastourismapp.presentation.detaildestination.DetailDestinationScreen
import com.jer.banyumastourismapp.presentation.home.HomeScreen
import com.jer.banyumastourismapp.presentation.home.HomeViewModel
import com.jer.banyumastourismapp.presentation.itinerary
import com.jer.banyumastourismapp.presentation.itinerary.ItineraryScreen
import com.jer.banyumastourismapp.presentation.listDestination
import com.jer.banyumastourismapp.presentation.listSosmed
import com.jer.banyumastourismapp.presentation.maps.MapsScreen
import com.jer.banyumastourismapp.presentation.maps.MapsViewModel
import com.jer.banyumastourismapp.presentation.navgraph.Route
import com.jer.banyumastourismapp.presentation.orders.OrdersFormScreen
import com.jer.banyumastourismapp.presentation.plan
import com.jer.banyumastourismapp.presentation.profile.ProfileScreen
import com.jer.banyumastourismapp.presentation.profile.ProfileViewModel
import com.jer.banyumastourismapp.presentation.profile.bookmark.BookmarkState
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
                label = "Maps",
                icon = R.drawable.mapsicon
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
            Route.MapsScreen.route -> 2
            Route.TicketHistoryScreen.route -> 3
            Route.ProfileScreen.route -> 4
            else -> 0
        }

    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
        backStackState?.destination?.route == Route.DestinationListScreen.route ||
        backStackState?.destination?.route == Route.MapsScreen.route ||
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
                            2 -> navigateToTab(navController, Route.MapsScreen.route)
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
                val viewModel: HomeViewModel = hiltViewModel()
                val profileViewModel: ProfileViewModel = hiltViewModel()
                val destinations = viewModel.destinations.collectAsLazyPagingItems()


                HomeScreen(
                    viewModel = profileViewModel,
                    destination = destinations,
                    navigateToDetail = { navigateToDetail(navController, it) },
                    navigateToItinerary = { navController.navigate(Route.ItineraryScreen.route) },
                    navigateToLogin = {navController.navigate(Route.LoginScreen.route)}
                )
            }
            composable(Route.LoginScreen.route) {
                val viewModel: AuthViewModel = hiltViewModel()

                LoginScreen(
                    navigateToHome = { navController.navigate(Route.HomeScreen.route) },
                    viewModel = viewModel
                )
            }
            composable(Route.DestinationListScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val destinations = viewModel.destinations.collectAsLazyPagingItems()
                DestinationListScreen(
                    destination = destinations,
                    navigateToDetail = { navigateToDetail(navController, it) }
                )
            }

            composable(Route.MapsScreen.route) {
//                SosmedListScreen(
//                    listSosmed = listSosmed,
//                    navigateToDetail = { navController.navigate(Route.DetailSosmedScreen.route) }
//                )
                val mapsViewModel: MapsViewModel = hiltViewModel()
                val destinations = mapsViewModel.destinations.collectAsLazyPagingItems()
                MapsScreen(
                    mapsViewModel = mapsViewModel,
                    listDestination = destinations,
                    navigateToDetail = { navigateToDetail(navController, it) }
                )
            }

            composable(Route.TicketHistoryScreen.route) {
            }

            composable (Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()

                ProfileScreen(
                    viewModel = viewModel,
                    state = BookmarkState(),
                    navigateToDetail = { /*TODO*/ })
            }

            composable(Route.DetailDestinationScreen.route) {

                navController.previousBackStackEntry?.savedStateHandle?.get<Destination>("destination")?.let {destination ->
                    DetailDestinationScreen(
                        detailDestination = destination,
                        navToOrders = {navController.navigate(Route.OrdersFormScreen.route)},
                        navBack = {navController.navigateUp()}
                    )
                }

            }

            composable(Route.DetailSosmedScreen.route) {

            }

            composable(Route.ItineraryScreen.route) {
                val auth = Firebase.auth
                val user = auth.currentUser
                ItineraryScreen(
                    user = user,
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

private fun navigateToDetail(navController: NavController, destination: Destination) {
    navController.currentBackStackEntry?.savedStateHandle?.set("destination", destination)
    navController.navigate(Route.DetailDestinationScreen.route)
}




