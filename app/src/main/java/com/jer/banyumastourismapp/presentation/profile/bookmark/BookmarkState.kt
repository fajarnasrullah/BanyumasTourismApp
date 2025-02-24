package com.jer.banyumastourismapp.presentation.profile.bookmark

import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.listDestination

data class BookmarkState(
    val listBookMarkDestination: List<Destination> = listDestination
//        emptyList()
)
