package com.jer.banyumastourismapp.presentation.sosmed

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme

@Composable
fun SosmedListScreen(
    modifier: Modifier = Modifier,
    listSosmed: List<Sosmed>,
    navigateToDetail: () -> Unit
)  {

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Column { 
                AddPostButton(onClick = navigateToDetail)
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier.padding(innerPadding)
        ) {

            val (bg, searchbar, addButton, sosmedList) = createRefs()


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


            val searchResult = remember {
                mutableStateOf("")
            }
            val isOnSearch = remember {
                mutableStateOf(true)
            }
            SearchBarForAll (
                hint = "Search Story",
                trailingIsVisible = true,
                query = searchResult,
                isOnSearch = isOnSearch,
                onSearchAction = {},
                modifier = Modifier
                    .constrainAs(searchbar) {
                        top.linkTo(parent.top)
                    }
                    .padding(30.dp),
            )



            SosmedListColumn(
                listSosmed = listSosmed,
                onClick = {},
                modifier = Modifier
                    .constrainAs(sosmedList) {
                        top.linkTo(bg.bottom)

                    }
                    .fillMaxHeight()

            )
        }

    }

}

@Composable
fun AddPostButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        onClick = { onClick() },
        modifier = Modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun SosmedListColumn(
    modifier: Modifier = Modifier,
    listSosmed: List<Sosmed>,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 250.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(listSosmed.size) { index ->
            SosmedCard(sosmed = listSosmed[index], onClick = onClick)
        }

    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrevSosmedListScreen() {
    BanyumasTourismAppTheme {
        val listSosmed = listOf(
            Sosmed(
                "Huru Hara 5 Hari di Bali",
                "Fajar",
                "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
            ),
            Sosmed(
                "Huru Hara 5 Hari di Bali",
                "Fajar",
                "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
            ),
            Sosmed(
                "Huru Hara 5 Hari di Bali",
                "Fajar",
                "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
            ),
            Sosmed(
                "Huru Hara 5 Hari di Bali",
                "Fajar",
                "Cerita pengalaman liburan di Bali ramai ramai selama 5 hari, ada seru dan mumet nya"
            )

        )
        SosmedListScreen(
            listSosmed = listSosmed,

            navigateToDetail = {})
    }
}