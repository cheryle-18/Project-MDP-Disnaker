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

Route::get('/users', [UserController::class, "getUsers"]);
Route::post('/register', [AuthController::class, "doRegister"]);
Route::post('/login', [AuthController::class, "doLogin"]);
Route::post('/autoLogin', [AuthController::class, "autoLogin"]);

Route::get('/kategori', [UtilityController::class, 'getKategori']);
Route::get('/pendidikan', [UtilityController::class, 'getPendidikan']);

Route::get('/peserta', [UserController::class, 'getPeserta']);

Route::prefix('perusahaan')->group(function () {
    Route::get('/', [UserController::class, 'getAllPerusahaan']);
    Route::get('/{perusahaan_id}', [UserController::class, 'getPerusahaan']);
    Route::post('/insert', [UserController::class, 'insertPerusahaan']);
    Route::post('/update', [UserController::class, 'updatePerusahaan'])->middleware('CekToken');
    Route::post('/password', [UserController::class, 'updatePasswordPerusahaan'])->middleware('CekToken');
});

Route::prefix('peserta')->group(function () {
    Route::get('/', [UserController::class, 'getAllPeserta']);
    Route::get('/{peserta_id}', [UserController::class, 'getPeserta']);
    Route::post('/update', [UserController::class, 'updatePeserta'])->middleware('CekToken');
    Route::post('/pendidikan', [UserController::class, 'updatePendidikan'])->middleware('CekToken');
    Route::post('/pendidikan/upload', [UserController::class, 'uploadIjazah'])->middleware('CekToken');
    Route::get('/riwayat/pelatihan', [UserController::class, 'getRiwayatPelatihan']);
    Route::get('/riwayat/pekerjaan', [UserController::class, 'getRiwayatPekerjaan']);
    Route::post('/kerja', [UserController::class, 'updateStatusKerja'])->middleware('CekToken');
    Route::get('/pendaftaran/{peserta_id}', [UserController::class, 'getPendaftaran']);
    Route::post('/password', [UserController::class, 'updatePasswordPeserta'])->middleware('CekToken');
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
    Route::post('/daftar', [LowonganController::class, 'daftarLowongan'])->middleware('CekToken');
});

Route::prefix('pelatihan')->group(function () {
    Route::get('/', [ApiPelatihanController::class, 'getAllPelatihan']);
    Route::get('/{pelatihan_id}', [ApiPelatihanController::class, 'getPelatihan']);
    Route::post('/insert', [ApiPelatihanController::class, 'insertPelatihan']);
    Route::post('/edit', [ApiPelatihanController::class, 'editPelatihan']);
    Route::post('/delete/{pelatihan_id}', [ApiPelatihanController::class, 'deletePelatihan']);
    Route::prefix('pendaftaran')->group(function () {
        Route::get('/', [PelatihanController::class, 'getPendaftaranPelatihan']);
        Route::get('/{pelatihan_id}', [ApiPelatihanController::class, 'getPendaftaran']);
    });
    Route::post('/daftar', [UserController::class, 'daftarPelatihan'])->middleware('CekToken');
});

Route::prefix('admin')->group(function () {
    Route::prefix('pelatihan')->group(function () {
        Route::prefix('pendaftaran')->group(function () {
            Route::get('/all', [PelatihanController::class, 'getAllPendaftaranPelatihan']);
            Route::post('/tolak', [PelatihanController::class, 'tolakPendaftaran']);
            Route::post('/terima', [PelatihanController::class, 'terimaPendaftaran']);
        });
    });
    Route::prefix('peserta')->group(function () {
        Route::get('/all', [PelatihanController::class, 'getAllPeserta']);
    });
    Route::prefix('perusahaan')->group(function () {
        Route::get('/all', [PelatihanController::class, 'getAllPerusahaan']);
    });
});
