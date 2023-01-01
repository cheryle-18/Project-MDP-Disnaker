<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use App\Models\Kategori;
use App\Models\Pelatihan;
use App\Models\Perusahaan;
use App\Models\Peserta;

class UtilityController extends Controller
{
    public function getKategori(){
        $kategori = Kategori::all();

        return response()->json([
            "kategori" => $kategori
        ], 200);
    }
    
    public function getPelatihan(){
        $pelatihan = Pelatihan::all();

        return response()->json([
            "pelatihan" => $pelatihan
        ], 200);
    }
}
