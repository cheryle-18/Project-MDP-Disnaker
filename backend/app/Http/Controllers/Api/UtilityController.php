<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use App\Models\Kategori;
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

    public function getPerusahaan(Request $req){
        $perusahaan = Perusahaan::find($req->perusahaan_id);

        return response()->json([
            "perusahaan" => $perusahaan
        ], 200);
    }

    public function getPeserta(Request $req){
        $peserta = Peserta::find($req->peserta_id);

        return response()->json([
            "peserta" => $peserta
        ], 200);
    }
    
    function getUsers()
    {
        return response()->json(User::all(), 200);
    }
}
