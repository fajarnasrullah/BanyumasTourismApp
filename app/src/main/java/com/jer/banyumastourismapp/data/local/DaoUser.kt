package com.jer.banyumastourismapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jer.banyumastourismapp.domain.model.User

@Dao
interface DaoUser {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE uid =:uid")
    suspend fun getUser(uid: String): User


}