<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use App\Models\Kategori;
use App\Models\Pelatihan;
use App\Models\Pendidikan;
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

    public function getPendidikan(){
        $temp = Pendidikan::all();
        $pendidikan = [];

        foreach($temp as $t){
            $pendidikan[] = [
                "pendidikan_id" => $t->pendidikan_id,
                "nama" => $t->nama
            ];
        }

        return response()->json([
            "pendidikan" => $pendidikan
        ], 200);
    }

    public function getPelatihan(){
        $pelatihan = Pelatihan::all();

        return response()->json([
            "pelatihan" => $pelatihan
        ], 200);
    }
}
