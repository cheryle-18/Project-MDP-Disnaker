<?php

namespace App\Http\Controllers;

use App\Models\Pelatihan;
use App\Models\PendaftaranPelatihan;
use App\Models\Pendidikan;
use App\Models\Peserta;
use DateTime;
use Illuminate\Http\Request;

class PelatihanController extends Controller
{
    public function getPendaftaranPelatihan(Request $req)
    {
        $pendaftaran = Pelatihan::all();
        // $pelatihan = [];
        // $peserta = [];

        $temp_pendaftaran = [];
        foreach($pendaftaran as $p){
            foreach($p->peserta as $pe){
                $peserta = Peserta::find($pe->peserta_id);
                $now = new DateTime(now());
                $tgl_lahir = new DateTime($peserta->tgl_lahir);
                $usia = $now->diff($tgl_lahir)->format("%Y");

                $pelatihan = Pelatihan::find($p->pelatihan_id);
                $pelatihan["pendidikan"] = (Pendidikan::find($pelatihan->pendidikan_id))->nama;

                $temp_pendaftaran[] = [
                    "pp_id" => $pe->pivot->pp_id,
                    "kategori" => $p->kategori->nama,
                    "pelatihan_nama" => $p->nama,
                    "status_pendaftaran" => $pe->pivot->status_pendaftaran,
                    "status_kelulusan" => $pe->pivot->status_kelulusan,
                    "tgl_pendaftaran" => date_format(date_create($pe->pivot->tgl_pendaftaran), "d M Y"),
                    "tgl_wawancara" => date_format(date_create($pe->pivot->tgl_wawancara), "d M Y"),
                    "status_kelulusan" => $pe->pivot->status_kelulusan,
                    "sisa_kuota" => $pelatihan->kuota - count(
                        PendaftaranPelatihan::
                            where("pelatihan_id","=",$pelatihan->pelatihan_id)->
                            where("status_pendaftaran",">=",2)->
                            where("status_kelulusan","!=",2)->
                            get()
                    ),
                    "peserta" => [
                        "user_id" => $peserta->user->user_id,
                        "nama" => $peserta->user->nama,
                        "email" => $peserta->user->email,
                        "username" => $peserta->user->username,
                        "password" => $peserta->user->password,
                        "telp" => $peserta->user->telp,
                        "role" => $peserta->user->role,
                        "peserta_id" => $peserta->peserta_id,
                        "nik" => $peserta->nik,
                        "pendidikan" => (Pendidikan::find($peserta->pendidikan_id))->nama,
                        "jurusan" => $peserta->jurusan,
                        "nilai" => $peserta->nilai,
                        "status" => $peserta->status,
                        "usia" => $usia,
                    ],
                    "pelatihan" => $pelatihan,
                ];

                // $peserta = Peserta::find($pe->peserta_id);
                // $peserta[] = [
                //     "user_id" => $peserta->user->user_id,
                //     "nama" => $peserta->user->nama,
                //     "email" => $peserta->user->email,
                //     "username" => $peserta->user->username,
                //     "password" => $peserta->user->password,
                //     "telp" => $peserta->user->telp,
                //     "role" => $peserta->user->role,
                //     "peserta_id" => $peserta->peserta_id,
                //     "nik" => $peserta->nik,
                //     "pendidikan" => $peserta->pendidikan,
                //     "jurusan" => $peserta->jurusan,
                //     "nilai" => $peserta->nilai,
                //     "status" => $peserta->status,
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
