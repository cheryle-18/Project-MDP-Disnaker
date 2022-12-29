package com.example.projectdisnaker.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //USERS
    @GET("users")
    fun getUsers(): Call<UserResponse>

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
    fun getKategori(): Call<KategoriResponse>

    @GET("pelatihan")
    fun getPelatihan(): Call<PelatihanResponse>

    @GET("pendaftaranPelatihan")
    fun getPendaftaranPelatihan(): Call<PendaftaranResponse>

    //PERUSAHAAN
    @GET("perusahaan")
    fun getPerusahaan(
        @Query("user_id") userId: Int
    ): Call<PerusahaanResponse>

    //LOWONGAN
    @GET("lowongan")
    fun getAllLowongan(): Call<LowonganResponse>

    @GET("lowongan/perusahaan")
    fun getPerusLowongan(
        @Query("perusahaan_id") perusahaanId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/get")
    fun getLowongan(
        @Query("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

}