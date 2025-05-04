package com.jer.banyumastourismapp.presentation.destination

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll

enum class SortType { ASC, DESC, NONE }

@Composable
fun DestinationListScreen(
    modifier: Modifier = Modifier,
    viewModel: DestinationListViewModel,
    destination: LazyPagingItems<Destination>,
    navigateToDetail: (Destination) -> Unit
) {

    val resultListDestination by viewModel.destination.collectAsState()
    var isLoading = rememberSaveable {
        mutableStateOf(true)
    }
    val query = rememberSaveable {
        mutableStateOf("")
    }
    var isOnSearch = rememberSaveable {
        mutableStateOf(false)
    }

    var isOnSorted by rememberSaveable {
        mutableStateOf(false)
    }

    var isOnClassified = rememberSaveable {
        mutableStateOf(false)
    }


//    LaunchedEffect(Unit) {
//        viewModel.destination.observeForever{
//            resultListDestination.clear()
//            resultListDestination.addAll(it)
//            Log.d("DestinationListScreen", "Cek resultListDestination: $resultListDestination")
//            isLoading = false
//        }
//    }

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var sortType by rememberSaveable {
        mutableStateOf(SortType.NONE)
    }

    var sortedList = getFilteredAndSortedList(destination, searchQuery, sortType)
    var selectedCategory = rememberSaveable {
        mutableStateOf("")
    }
    var destinationByCategory = getDestinationListByCategory(destination, selectedCategory.value)


    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier

                .padding(innerPadding)
//                .verticalScroll(scrollState)
        ) {
            val (bg, searchbar, category, destinationList, progress) = createRefs()

            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .constrainAs(bg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            SearchBarForAll(
                query = query,
                hint = "Search Destination",
                trailingIsVisible = true,
                isOnSearch = isOnSearch,
                onSearchAction = {
                    isOnSearch.value = true
                    viewModel.destinationByTitle(query.value)
                    Log.d("DestinationListScreen", "resultListDestination: ${resultListDestination}")
                },
                onSortedAsc = {
                    sortType = SortType.ASC
                    isOnSorted = true
                },
                onSortedDesc = {
                    sortType = SortType.DESC
                    isOnSorted = true
                },
                resetSort = {
                    sortType = SortType.NONE
                    isOnSorted = false
                },
                modifier = Modifier
                    .constrainAs(searchbar) {
                        top.linkTo(parent.top)
                    }
                    .padding(30.dp)
            )

            CategoryRow(
                isDelay = false,
                selectedCategory = selectedCategory,
                isOnClassified = isOnClassified,
                modifier = Modifier
                    .constrainAs(category) {
                        top.linkTo(bg.bottom)

                    }
                    .padding(top = 30.dp, bottom = 15.dp),


            )

            if (isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    trackColor = Color.Gray,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(100.dp)
                        .constrainAs(progress) {
                            top.linkTo(bg.bottom)

                            start.linkTo(parent.start)

                        }
                        .padding(top = 250.dp, start = 150.dp)
                )
            }
                DestinationCardLandscapeColumn(
                    destination = destination,
                    destinationFromCategory = destinationByCategory,
                    destinationFromFilter = sortedList,
                    destinationFromSearch = resultListDestination,
                    onClick = { navigateToDetail(it) },
                    isOnSearch = isOnSearch.value,
                    isOnSorted = isOnSorted,
                    isOnClassified = isOnClassified.value,
                    isLoading = isLoading,
                    modifier = Modifier
                        .constrainAs(destinationList) {
                            top.linkTo(category.bottom)

                        }
                        .fillMaxHeight()
                )



            

            DisposableEffect(key1 = Unit) {
                onDispose {
                    isOnClassified.value = false
                }

            }
        }
    }



}

@Composable
fun DestinationCardLandscapeColumn(
    modifier: Modifier = Modifier,
    destination: LazyPagingItems<Destination>,
    destinationFromCategory: List<Destination>,
    destinationFromFilter: List<Destination>,
    destinationFromSearch: List<Destination>,
    isOnSearch: Boolean,
    isOnSorted: Boolean,
    isOnClassified: Boolean,
    onClick: (Destination) -> Unit,
    isLoading: MutableState<Boolean>,
) {


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues( start = 30.dp, end = 30.dp, bottom = 320.dp, top = 20.dp),

        modifier = modifier
    ) {


            if (isOnSearch) {
                isLoading.value = false
                items(count = destinationFromSearch.size) { index ->
                    DestinationCardLandscape(
                        destination = destinationFromSearch[index],
                        onClick = { onClick(it) },
                        buttonVisibility = false
                    )
                }
            } else if (isOnSorted) {
                isLoading.value = false
                items(destinationFromFilter) { item ->

                    DestinationCardLandscape(
                        destination = item,
                        onClick = { onClick(it) },
                        buttonVisibility = false
                    )
                }
            } else if (isOnClassified) {
                isLoading.value = false
                items(destinationFromCategory) { item ->

                    DestinationCardLandscape(
                        destination = item,
                        onClick = { onClick(it) },
                        buttonVisibility = false
                    )
                }
            } else {
                isLoading.value = false
                items(count = destination.itemCount) { index ->

                    destination[index]?.let {
                        DestinationCardLandscape(
                            destination = it,
                            onClick = { onClick(it) },
                            buttonVisibility = false
                        )
                    }
                }
            }


    }
}

fun getFilteredAndSortedList(destination: LazyPagingItems<Destination>, searchQuery: String, sortType: SortType): List<Destination> {
    val filtered = destination.itemSnapshotList.items.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    return when (sortType) {
        SortType.ASC -> filtered.sortedBy { it.title }
        SortType.DESC -> filtered.sortedByDescending { it.title }
        else -> filtered
    }
}

fun getDestinationListByCategory(destination: LazyPagingItems<Destination>, category: String): List<Destination> {
    val filtered = destination.itemSnapshotList.items.filter {
        it.category.contains(category, ignoreCase = true)
    }
    return filtered
}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun PreviewDestinationListScreen() {
//    BanyumasTourismAppTheme {
//        DestinationListScreen(
//            user = User("Fajar"),
//                destination = listDestination,
//                    navigateToDetail = {}
//        )
//    }
//
//}

