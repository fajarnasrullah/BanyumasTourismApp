package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Destination(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val title: String = "",
    val location: String = "",
    val description: String? = null ,
    val cost: Int = 0,
    val imageUrl: String? = null
): Parcelable
