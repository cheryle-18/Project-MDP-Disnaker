<?php

namespace App\Http\Controllers;

use App\Models\Kategori;
use App\Models\Perusahaan;
use App\Models\Peserta;
use Illuminate\Http\Request;

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
}
