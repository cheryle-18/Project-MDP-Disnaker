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

    @GET("lowongan/perusahaan/{perusahaan_id}")
    fun getPerusLowongan(
        @Path("perusahaan_id") perusahaanId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/{lowongan_id}")
    fun getLowongan(
        @Path("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

    @POST("lowongan/insert")
    fun insertLowongan(
        @Query("kategori") kategori: String,
        @Query("perusahaan_id") perusahaanId: Int,
        @Body lowongan: LowonganItem
    ): Call<LowonganResponse>

    @POST("lowongan/update/{lowongan_id}")
    fun updateLowongan(
        @Path("lowongan_id") lowonganId: Int,
        @Query("kategori") kategori: String,
        @Body lowongan: LowonganItem
    ): Call<LowonganResponse>

    @POST("lowongan/delete/{lowongan_id}")
    fun deleteLowongan(
        @Path("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

    @POST("lowongan/tutup/{lowongan_id}")
    fun tutupLowongan(
        @Path("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/pendaftaran/{lowongan_id}")
    fun getPendaftaran(
        @Path("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>
}