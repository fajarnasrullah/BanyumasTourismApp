package com.jer.banyumastourismapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import kotlinx.coroutines.delay

data class Category(
    val text: String,
    val icon: Painter? = null
)


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card (
        modifier = Modifier
            .height(37.dp)
            .clickable { onClick() },
        shape = CircleShape,

        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)  MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outlineVariant ,
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp,)

    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(all = 5.dp)
        ) {

            if ( icon != null) {

                Surface (
                    shape = CircleShape,
                    color = Color.White,
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(29.dp),
                    ) {
                        Image(
                            painter = icon,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(verySmallIcon)
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.width(5.dp))

            Text (
                text = text,
                textAlign = TextAlign.Center,
                fontSize = TextUnit(10f, TextUnitType.Sp),

                )

            Spacer(modifier = Modifier.width(5.dp))
        }

    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    isDelay: Boolean,
    isDummy: Boolean = false,
    selectedCategory: MutableState<String>? = null,
    isOnClassified: MutableState<Boolean>? = null,
) {

    val categories = listOf(
        Category("All", painterResource(id = R.drawable.allicon)),
        Category("Mountain", painterResource(id = R.drawable.mountainicon)),
        Category("Play Ground", painterResource(id = R.drawable.beachicon)),
        Category("Waterfall", painterResource(id = R.drawable.waterfallicon)),
        Category("Temple", painterResource(id = R.drawable.tampleiconsvg)),
        Category("Park", painterResource(id = R.drawable.parkicon)),
        Category("Museum", painterResource(id = R.drawable.museumicon)),
        Category("Forest", painterResource(id = R.drawable.foresticon)),
        Category("Lake", painterResource(id = R.drawable.lakeicon)),

    )

    val forDummy = listOf(
        Category("Mountain", painterResource(id = R.drawable.mountainicon)),
        Category("Play Ground", painterResource(id = R.drawable.beachicon)),
        Category("Waterfall", painterResource(id = R.drawable.waterfallicon)),
        Category("Temple", painterResource(id = R.drawable.tampleiconsvg)),
        Category("Park", painterResource(id = R.drawable.parkicon)),
        Category("Museum", painterResource(id = R.drawable.museumicon)),
        Category("Forest", painterResource(id = R.drawable.foresticon)),
        Category("Lake", painterResource(id = R.drawable.lakeicon)),
        )

    val listState = rememberLazyListState()
    val selectedIndex = remember { mutableStateOf(-1) }


    LaunchedEffect(key1 = Unit) {
        while (true && isDelay) {
            listState.animateScrollToItem(index = (listState.firstVisibleItemIndex + 1) % categories.size)
            delay(2000)
        }

    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 30.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            if (isDummy) forDummy else categories
        ) { index, category ->
            CategoryCard(
                text = category.text,
                icon = category.icon,
                isSelected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index

                    if (isOnClassified != null) {
                        isOnClassified.value = true
                        if (selectedIndex.value == -1 || selectedIndex.value == 0) {
                            isOnClassified.value = false
                        }
                    }
                    if (selectedCategory != null) {
                        selectedCategory.value = category.text
                    }
                }
            )
        }
    }
}