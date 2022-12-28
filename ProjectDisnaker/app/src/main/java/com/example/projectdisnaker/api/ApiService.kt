package com.example.projectdisnaker.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //USERS
    @GET("users")
    fun getUsers(): Call<ArrayList<UserResponseItem>>

    @POST("register")
    fun register(
        @Query("nama") nama:String,
        @Query("username") username:String,
        @Query("password") password:String,
        @Query("role") role:Int,
    ): Call<UserResponse>

    @POST("login")
    fun login(
        @Query("username") username:String,
        @Query("password") password:String,
    ): Call<UserResponse>

    //KATEGORI
    @GET("kategori")
    fun getKategori(): Call<ArrayList<UserResponseItem>>
}