package com.jer.banyumastourismapp.presentation.navgraph

sealed class Route(
    val route: String,

) {
    object AppStartNavigation: Route(route = "appStartNavigation")
    object RegisterScreen: Route(route = "registerScreen")
    object LoginScreen: Route(route = "loginScreen")
    object HomeScreen: Route(route = "homeScreen")
    object DestinationListScreen: Route(route = "destinationListScreen")
    object DetailDestinationScreen: Route(route = "detailDestinationScreen")
    object SosmedListScreen: Route(route = "sosmedListScreen")
    object DetailSosmedScreen: Route(route = "detailSosmedScreen")
    object ItineraryScreen: Route(route = "itineraryScreen")
    object TicketHistoryScreen: Route(route = "ticketHistoryScreen")
    object ProfileScreen: Route(route = "profileScreen")
    object BookmarkScreen: Route(route = "bookmarkScreen")
    object TransactionScreen: Route(route = "transactionScreen")
}