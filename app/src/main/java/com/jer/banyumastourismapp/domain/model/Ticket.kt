package com.jer.banyumastourismapp.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("uid"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("uid") val uid: String = "",
    val title: String? = null,
    val image: String? = null,
    val category: String? = null,
    val name: String? = null,
    val date: String? = null,
    val location: String? = null,
    val price: Int = 0,
    val qty: Int = 0,
    val createdAt: Long
): Parcelable
