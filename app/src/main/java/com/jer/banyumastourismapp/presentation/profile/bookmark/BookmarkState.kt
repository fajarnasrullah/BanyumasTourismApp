package com.jer.banyumastourismapp.presentation.profile.bookmark

import com.jer.banyumastourismapp.presentation.component.Destination
import com.jer.banyumastourismapp.presentation.listDestination

data class BookmarkState(
    val listBookMarkDestination: List<Destination> = listDestination
//        emptyList()
)
