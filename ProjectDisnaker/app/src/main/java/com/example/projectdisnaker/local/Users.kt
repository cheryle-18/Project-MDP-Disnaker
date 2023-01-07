package com.example.projectdisnaker.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class Users(
    @PrimaryKey(autoGenerate = false)
    var api_key: String,
) {
}