package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey val uid: String = "",
    val name: String? = null,
    val email: String? = null,
    val desc: String? = null,
    val provider: String = "",
    ): Parcelable
