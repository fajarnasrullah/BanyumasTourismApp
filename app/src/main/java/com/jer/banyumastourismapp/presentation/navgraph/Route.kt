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
    object MapsScreen: Route(route = "mapsScreen")
    object StoryScreen: Route(route = "storyScreen")
    object DetailSosmedScreen: Route(route = "detailSosmedScreen")
    object ItineraryScreen: Route(route = "itineraryScreen")
    object ItineraryFormScreen: Route(route = "itineraryFormScreen")
    object TicketScreen: Route(route = "ticketHistoryScreen")
    object ProfileScreen: Route(route = "profileScreen")
    object BookmarkScreen: Route(route = "bookmarkScreen")
    object OrdersFormScreen: Route(route = "transactionScreen")
    object PaymentLoadingScreen: Route(route = "paymentLoadingScreen")
    object PaymentWebViewScreen: Route(route = "paymentWebViewScreen")
}