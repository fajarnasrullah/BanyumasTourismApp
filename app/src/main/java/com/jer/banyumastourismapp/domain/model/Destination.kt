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
    val imageUrl: String? = "",
    val description: String? = "" ,
    val location: String = "",
    val cost: Int = 0,
): Parcelable
