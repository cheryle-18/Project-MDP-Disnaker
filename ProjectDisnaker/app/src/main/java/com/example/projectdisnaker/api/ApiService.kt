package com.example.projectdisnaker.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //USERS
    @GET("users")
    fun getUsers(): Call<UserResponse>

    @POST("register")
    fun register(
        @Body register: LoginItem
    ): Call<UserResponse>

    @POST("login")
    fun login(
        @Body login: LoginItem
    ): Call<UserResponse>

    @POST("autoLogin")
    fun autoLogin(
        @Query("api_key") apiKey: String
    ): Call<UserResponse>

    //PESERTA PROFILE
    @GET("peserta/{peserta_id}")
    fun getPeserta(
        @Path("peserta_id") pesertaId: Int
    ): Call<PesertaResponse>

    @POST("peserta/update")
    fun updatePeserta(
        @Query("api_key") token: String,
        @Body peserta: PesertaItem
    ): Call<PesertaResponse>

    @POST("peserta/password")
    fun updatePasswordPerserta(
        @Query("api_key") token: String,
        @Body pass: PasswordItem
    ): Call<PesertaResponse>

    @POST("peserta/pendidikan")
    fun updatePendidikan(
        @Query("api_key") token: String,
        @Body pendidikanItem: PendidikanItem
    ): Call<PendidikanResponse>

    @Multipart
    @POST("peserta/pendidikan/upload")
    fun uploadIjazah(
        @Query("api_key") token: String,
        @Part ijazah: MultipartBody.Part
    ): Call<UserResponse>

    @GET("peserta/riwayat/pelatihan")
    fun getRiwayatPelatihan(
        @Query("peserta_id") pesertaId: Int
    ): Call<RiwayatResponse>

    @GET("peserta/riwayat/pekerjaan")
    fun getRiwayatPekerjaan(
        @Query("peserta_id") pesertaId: Int
    ): Call<RiwayatResponse>

    @POST("peserta/kerja")
    fun updateStatusKerja(
        @Query("api_key") token: String,
    ): Call<PesertaResponse>

    //KATEGORI
    @GET("kategori")
    fun getKategori(): Call<KategoriResponse>

    //PENDIDIKAN
    @GET("pendidikan")
    fun getPendidikan(): Call<PendidikanResponse>

    //PELATIHAN
    @GET("pelatihan")
    fun getPelatihan(
        @Query("user_role") userRole:String,
        @Query("peserta_id") pesertaId:Int = -1
    ): Call<PelatihanResponse>

    @GET("pelatihan")
    fun getPelatihanWithQuery(
        @Query("search") search:String,
        @Query("user_role") userRole:String,
        @Query("peserta_id") pesertaId:Int = -1
    ): Call<PelatihanResponse>

    @GET("pelatihan/{pelatihan_id}")
    fun getOnePelatihan(
        @Path("pelatihan_id") pelatihanId: Int
    ): Call<PelatihanResponse>

    @POST("pelatihan/insert")
    fun insertPelatihan(
        @Body pelatihan: PelatihanItem
    ): Call<PelatihanResponse>

    @POST("pelatihan/edit")
    fun editPelatihan(
        @Body pelatihan: PelatihanItem
    ): Call<PelatihanResponse>

    @POST("pelatihan/delete/{pelatihan_id}")
    fun deletePelatihan(
        @Path("pelatihan_id") pelatihanId: Int
    ): Call<PelatihanResponse>

    @GET("pelatihan/pendaftaran/{pelatihan_id}")
    fun getPesertaPelatihan(
        @Path("pelatihan_id") pelatihanId: Int
    ): Call<PesertaPendaftaranResponse>

    @POST("pelatihan/daftar")
    fun daftarPelatihan(
        @Query("api_key") token: String,
        @Query("pelatihan_id") pelatihanId: Int
    ): Call<PelatihanResponse>

    @GET("admin/pelatihan/pendaftaran/all")
    fun getAllPendaftaranPelatihan(): Call<PendaftaranResponse>

    @POST("admin/pelatihan/pendaftaran/terima")
    fun terimaPendaftaran(
        @Query("pp_id") pp_id: Int,
        @Query("tgl_wawancara") tgl_wawancara: String,
    ): Call<PendaftaranResponse>

    @POST("admin/pelatihan/pendaftaran/tolak")
    fun tolakPendaftaran(
        @Query("pp_id") pp_id: Int,
    ): Call<PendaftaranResponse>

    @GET("admin/peserta/all")
    fun getAllPeserta(): Call<UserResponse>

    @GET("admin/perusahaan/all")
    fun getAllPerusahaan(): Call<UserResponse>

    @GET("peserta/pendaftaran/{peserta_id}")
    fun getPesertaPendaftaran(
        @Path("peserta_id") pesertaId: Int
    ): Call<StatusPendaftaranResponse>

    //PERUSAHAAN
    @GET("perusahaan/{perusahaan_id}")
    fun getPerusahaan(
        @Path("perusahaan_id") perusahaanId: Int
    ): Call<PerusahaanResponse>

    @POST("perusahaan/insert")
    fun insertPerusahaan(
        @Body perusahaan: PerusahaanItem
    ): Call<PerusahaanResponse>

    @POST("perusahaan/update")
    fun updatePerusahaan(
        @Query("api_key") token: String,
        @Body perusahaan: PerusahaanItem
    ): Call<PerusahaanResponse>

    @POST("perusahaan/password")
    fun updatePasswordPerusahaan(
        @Query("api_key") token: String,
        @Body pass: PasswordItem
    ): Call<PerusahaanResponse>

    //LOWONGAN
    @GET("lowongan")
    fun getAllLowongan(): Call<LowonganResponse>

    @GET("lowongan/perusahaan/{perusahaan_id}")
    fun getPerusLowongan(
        @Path("perusahaan_id") perusahaanId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/peserta/{peserta_id}")
    fun getPesertaLowongan(
        @Path("peserta_id") pesertaId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/{lowongan_id}")
    fun getLowongan(
        @Path("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

    @GET("lowongan/{lowongan_id}")
    fun getPesertaDetLow(
        @Path("lowongan_id") lowonganId: Int,
        @Query("peserta_id") pesertaId: Int
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
    fun getPendaftaranLowongan(
        @Path("lowongan_id") lowonganId: Int
    ): Call<PesertaPendaftaranResponse>

    @POST("lowongan/daftar")
    fun daftarLowongan(
        @Query("api_key") token: String,
        @Query("lowongan_id") lowonganId: Int
    ): Call<LowonganResponse>

}