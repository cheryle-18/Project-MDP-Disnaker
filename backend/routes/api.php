<?php

use App\Http\Controllers\LowonganController;
use App\Http\Controllers\UtilityController;
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

Route::get('/kategori', [UtilityController::class, 'getKategori']);
Route::get('/perusahaan/{perusahaan_id}', [UtilityController::class, 'getPerusahaan']);
Route::get('/peserta/{peserta_id}', [UtilityController::class, 'getPeserta']);

Route::prefix('lowongan')->group(function () {
    Route::get('/', [LowonganController::class, 'getAllLowongan']);
    Route::get('/get/{lowongan_id}', [LowonganController::class, 'getLowongan']);
    Route::post('/insert', [LowonganController::class, 'insertLowongan']);
    Route::post('/update', [LowonganController::class, 'updateLowongan']);
    Route::post('/delete', [LowonganController::class, 'deleteLowongan']);
    Route::post('/tutup', [LowonganController::class, 'tutupLowongan']);
    Route::get('/pendaftaran/{lowongan_id}', [LowonganController::class, 'getPendaftaran']);
    Route::post('/daftar/{lowongan_id}', [LowonganController::class, 'daftarLowongan']);
});
