<?php

namespace App\Http\Controllers;

use App\Models\Pelatihan;
use App\Models\Peserta;
use Illuminate\Http\Request;

class PelatihanController extends Controller
{
    public function getPendaftaranPelatihan(Request $req)
    {
        $pendaftaran = Pelatihan::all();
        $pelatihan = [];
        $peserta = [];

        $temp_pendaftaran = [];
        foreach($pendaftaran as $p){
            foreach($p->peserta as $pe){
                $temp_peserta = Peserta::find($pe->peserta_id);
                $temp_pendaftaran[] = [
                    "pp_id" => $pe->pivot->pp_id,
                    "kategori" => $p->kategori->nama,
                    "pelatihan_nama" => $p->nama,
                    "status_pendaftaran" => $pe->pivot->status_pendaftaran,
                    "status_kelulusan" => $pe->pivot->status_kelulusan,
                    "peserta" => [
                        "user_id" => $temp_peserta->user->user_id,
                        "nama" => $temp_peserta->user->nama,
                        "email" => $temp_peserta->user->email,
                        "username" => $temp_peserta->user->username,
                        "password" => $temp_peserta->user->password,
                        "telp" => $temp_peserta->user->telp,
                        "role" => $temp_peserta->user->role,
                        "peserta_id" => $temp_peserta->peserta_id,
                        "nik" => $temp_peserta->nik,
                        "pendidikan_id" => $temp_peserta->pendidikan_id,
                        "jurusan" => $temp_peserta->jurusan,
                        "nilai" => $temp_peserta->nilai,
                        "status" => $temp_peserta->status,
                    ],
                    "pelatihan" => Pelatihan::find($p->pelatihan_id),
                ];
                // $temp_peserta = Peserta::find($pe->peserta_id);
                // $peserta[] = [
                //     "user_id" => $temp_peserta->user->user_id,
                //     "nama" => $temp_peserta->user->nama,
                //     "email" => $temp_peserta->user->email,
                //     "username" => $temp_peserta->user->username,
                //     "password" => $temp_peserta->user->password,
                //     "telp" => $temp_peserta->user->telp,
                //     "role" => $temp_peserta->user->role,
                //     "peserta_id" => $temp_peserta->peserta_id,
                //     "nik" => $temp_peserta->nik,
                //     "pendidikan" => $temp_peserta->pendidikan,
                //     "jurusan" => $temp_peserta->jurusan,
                //     "nilai" => $temp_peserta->nilai,
                //     "status" => $temp_peserta->status,
                // ];


                // "user_id" => $user->user_id,
                // "nama" => $user->nama,
                // "email" => $user->email,
                // "username" => $user->username,
                // "password" => $user->password,
                // "telp" => $user->telp,
                // "role" => $user->role,
                // "peserta_id" => $peserta->peserta_id,
                // "nik" => $peserta->nik,
                // "pendidikan" => $peserta->pendidikan,
                // "jurusan" => $peserta->jurusan,
                // "nilai" => $peserta->nilai,
                // "status" => $peserta->status,
            }
        }
        $pendaftaran = $temp_pendaftaran;


        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil fetch"
        ], 200);
    }
}
