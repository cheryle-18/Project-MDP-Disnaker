<?php

use App\Http\Controllers\Api\AuthController;
use App\Http\Controllers\Api\UtilityController;
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
// Route::post('/mahasiswa/insert', [MahasiswaController::class, "insertMhs"]);
// Route::post('/mahasiswa/update', [MahasiswaController::class, "updateMhs"]);
// Route::post('/mahasiswa/delete', [MahasiswaController::class, "deleteMhs"]);

