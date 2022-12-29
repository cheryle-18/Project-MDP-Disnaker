<?php

namespace App\Http\Controllers;

use App\Models\Pelatihan;
use Illuminate\Http\Request;

class PelatihanController extends Controller
{
    public function getPendaftaranPelatihan(Request $req)
    {
        $pelatihan = Pelatihan::all();

        $temp_pelatihan = [];
        foreach($pelatihan as $p){
            foreach($p->peserta as $pe){
                $temp_pelatihan[] = [
                    "pp_id" => $pe->pivot->pp_id,
                    "pelatihan_nama" => $p->nama,
                    "status_pendaftaran" => $pe->pivot->status_pendaftaran,
                    "status_kelulusan" => $pe->pivot->status_kelulusan,
                ];
            }
        }
        $pelatihan = $temp_pelatihan;


        return response()->json([
            "pelatihan" => $pelatihan,
            // "user" => $pendaftaran_pelatihan[0]->peserta->user,
            "message" => "Berhasil fetch"
        ], 200);
    }
}
