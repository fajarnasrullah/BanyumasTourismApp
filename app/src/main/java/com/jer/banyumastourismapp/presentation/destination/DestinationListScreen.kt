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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll
import dev.chrisbanes.haze.HazeState

@Composable
fun DestinationListScreen(
    modifier: Modifier = Modifier,
    viewModel: DestinationListViewModel,
    destination: LazyPagingItems<Destination>,
    navigateToDetail: (Destination) -> Unit
) {

    val resultListDestination by viewModel.destination.collectAsState()
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }
    val query = rememberSaveable {
        mutableStateOf("")
    }
    var isOnSearch = rememberSaveable {
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

    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier

                .padding(innerPadding)
//                .verticalScroll(scrollState)
        ) {
            val (bg, searchbar, category, destinationList) = createRefs()

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
                    isLoading = false
                    Log.d("DestinationListScreen", "resultListDestination: ${resultListDestination}")
                },
                modifier = Modifier
                    .constrainAs(searchbar) {
                        top.linkTo(parent.top)


                    }
                    .padding(30.dp)
            )

            CategoryRow(
                isDelay = false,
                modifier = Modifier
                    .constrainAs(category) {
                        top.linkTo(bg.bottom)

                    }
                    .padding(top = 30.dp, bottom = 15.dp),

            )

            DestinationCardLandscapeColumn(
                destination = destination,
                destinationFromSearch = resultListDestination,
                onClick = { navigateToDetail(it) },
                isOnSearch = isOnSearch.value,
                modifier = Modifier
                    .constrainAs(destinationList) {
                        top.linkTo(category.bottom)

                    }
                    .fillMaxHeight()
            )
            

        }
    }



}

@Composable
fun DestinationCardLandscapeColumn(
    modifier: Modifier = Modifier,
    destination: LazyPagingItems<Destination>,
    destinationFromSearch: List<Destination>,
    isOnSearch: Boolean,
    onClick: (Destination) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues( start = 30.dp, end = 30.dp, bottom = 320.dp, top = 20.dp),

        modifier = modifier
    ) {
        if (isOnSearch) {
            items(count = destinationFromSearch.size) { index ->
                DestinationCardLandscape(
                    destination = destinationFromSearch[index],
                    onClick = { onClick(it) },
                    buttonVisibility = false
                )
            }
        } else {
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

