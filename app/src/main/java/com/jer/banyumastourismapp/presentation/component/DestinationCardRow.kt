package com.jer.banyumastourismapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.jer.banyumastourismapp.domain.model.Destination

@Composable
fun DestinationCardStandRow(
    modifier: Modifier = Modifier,
    destination: LazyPagingItems<Destination>,
    onClick: (Destination) -> Unit
) {

    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
        modifier = modifier.fillMaxWidth(),

        ) {
        items(count = destination.itemCount) {count ->
            destination[count]?.let {
                DestinationCardPotrait(destination = it, onClick = {onClick(it)})
            }
        }
    }

}