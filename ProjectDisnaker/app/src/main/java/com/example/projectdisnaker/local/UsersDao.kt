package com.example.projectdisnaker.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("select * from users")
    fun getUserToken(): Users

    @Insert
    suspend fun saveToken(users: Users)

    @Query("delete from users")
    suspend fun removeToken()
}