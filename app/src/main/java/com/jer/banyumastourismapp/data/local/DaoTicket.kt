package com.jer.banyumastourismapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jer.banyumastourismapp.domain.model.Ticket

@Dao
interface DaoTicket {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)

    @Query("SELECT * FROM Ticket WHERE uid =:uid ORDER BY createdAt DESC LIMIT 1")
    suspend fun getTicket(uid: String): Ticket


}