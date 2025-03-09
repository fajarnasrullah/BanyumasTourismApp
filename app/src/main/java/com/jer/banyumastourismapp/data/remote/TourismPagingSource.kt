package com.jer.banyumastourismapp.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.domain.model.Destination
import kotlinx.coroutines.tasks.await

class TourismPagingSource(
    private val db: FirebaseDatabase
): PagingSource<Int, Destination>() {

    var totalDestiCount = 0

    override fun getRefreshKey(state: PagingState<Int, Destination>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Destination> {
        val page = params.key ?: 1
        return try {
            val snapshot = db.getReference("destinations").get().await()
//            snapshot.getValue(Destination::class.java)
//            totalDestiCount += snapshot.childrenCount.toInt()
            Log.d("TourismPagingSource","Succeed to get Destination From Realtime Database")
            val listDestinations = mutableListOf<Destination>()
            for (destinationSnapshot in snapshot.children) {
                val destination = destinationSnapshot.getValue(Destination::class.java)
                destination?.let {
                    val tempDestination = Destination(
                        id = listDestinations.size,
                        title = it.title,
                        imageUrl = it.imageUrl,
                        imageList = it.imageList,
                        description = it.description,
                        location = it.location,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        cost = it.cost,
                        timeOpen = it.timeOpen,
                        rating = it.rating,
                        ig = it.ig,
                        igUrl = it.igUrl,
                        facility = it.facility,
                        route = it.route,
                        accessibility = it.accessibility,
                        tips = it.tips

                    )
                    listDestinations.add(tempDestination)
                }
            }
            LoadResult.Page(
                data = listDestinations,
                prevKey = null,
//                if (page == 1) null else page - 1,
                nextKey = null
//                if (totalDestiCount == snapshot.childrenCount.toInt()) null else page + 1
            )

        }  catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}