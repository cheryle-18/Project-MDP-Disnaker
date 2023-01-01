<?php

use App\Http\Controllers\Api\AuthController;
use App\Http\Controllers\Api\UtilityController;
use App\Http\Controllers\Api\LowonganController;
use App\Http\Controllers\Api\PelatihanController as ApiPelatihanController;
use App\Http\Controllers\Api\UserController;
use App\Http\Controllers\PelatihanController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('/users', [UtilityController::class, "getUsers"]);
Route::post('/register', [AuthController::class, "doRegister"]);
Route::post('/login', [AuthController::class, "doLogin"]);

Route::get('/kategori', [UtilityController::class, 'getKategori']);
// Route::get('/pelatihan', [UtilityController::class, 'getPelatihan']);

Route::get('/peserta', [UserController::class, 'getPeserta']);
Route::get('/pendaftaranPelatihan', [PelatihanController::class, 'getPendaftaranPelatihan']);

Route::prefix('perusahaan')->group(function () {
    Route::get('/', [UserController::class, 'getAllPerusahaan']);
    Route::get('/{user_id}', [UserController::class, 'getPerusahaan']);
    Route::post('/update/{perusahaan_id}', [UserController::class, 'updatePerusahaan']);
});

Route::prefix('peserta')->group(function () {
    Route::get('/', [UserController::class, 'getAllPeserta']);
    Route::get('/{user_id}', [UserController::class, 'getPeserta']);
    Route::post('/update/{peserta_id}', [UserController::class, 'updatePeserta']);
    Route::post('/pendidikan/{peserta_id}', [UserController::class, 'updatePendidikan']);
    Route::get('/pelatihan/{peserta_id}', [UserController::class, 'getRiwayatPelatihan']);
    Route::post('/kerja/{peserta_id}', [UserController::class, 'updateStatusKerja']);
});

Route::prefix('lowongan')->group(function () {
    Route::get('/', [LowonganController::class, 'getAllLowongan']);
    Route::get('/perusahaan/{perusahaan_id}', [LowonganController::class, 'getLowonganPerusahaan']);
    Route::get('/peserta/{peserta_id}', [LowonganController::class, 'getLowonganPeserta']);
    Route::get('/{lowongan_id}', [LowonganController::class, 'getLowongan']);
    Route::post('/insert', [LowonganController::class, 'insertLowongan']);
    Route::post('/update/{lowongan_id}', [LowonganController::class, 'updateLowongan']);
    Route::post('/delete/{lowongan_id}', [LowonganController::class, 'deleteLowongan']);
    Route::post('/tutup/{lowongan_id}', [LowonganController::class, 'tutupLowongan']);
    Route::get('/pendaftaran/{lowongan_id}', [LowonganController::class, 'getPendaftaran']);
    Route::post('/daftar', [LowonganController::class, 'daftarLowongan']);
});


Route::prefix('pelatihan')->group(function () {
    Route::get('/', [ApiPelatihanController::class, 'getAllPelatihan']);
    Route::post('/insert', [ApiPelatihanController::class, 'insertPelatihan']);
    // Route::post('/update/{lowongan_id}', [LowonganController::class, 'updateLowongan']);
    // Route::post('/delete/{lowongan_id}', [LowonganController::class, 'deleteLowongan']);
    // Route::post('/tutup/{lowongan_id}', [LowonganController::class, 'tutupLowongan']);
    // Route::get('/pendaftaran/{lowongan_id}', [LowonganController::class, 'getPendaftaran']);
    // Route::post('/daftar', [LowonganController::class, 'daftarLowongan']);
    Route::get('/pendaftaran/{pelatihan_id}', [ApiPelatihanController::class, 'getPendaftaran']);
});
