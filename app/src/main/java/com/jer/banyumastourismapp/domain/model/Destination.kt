package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Destination(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val imageList: List<String> = emptyList(),
    val description: String = "",
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val cost: Int = 0,
    val timeOpen: String = "",
    val rating: Double = 0.0,
    val ig: String = "",
    val igUrl: String = "",
    val facility: List<Facility> = emptyList(),
    val route: String = "",
    val accessibility: List<String> = emptyList(),
    val tips: List<String> = emptyList()

): Parcelable

@Parcelize
data class Facility(
    val text: String = "",
    val icon: String = "",
): Parcelable
