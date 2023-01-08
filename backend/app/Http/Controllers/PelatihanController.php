<?php

namespace App\Http\Controllers;

use App\Models\Lowongan;
use App\Models\Pelatihan;
use App\Models\PendaftaranPelatihan;
use App\Models\Pendidikan;
use App\Models\Peserta;
use App\Models\User;
use DateTime;
use Illuminate\Http\Request;

class PelatihanController extends Controller
{
    public function getAllPendaftaranPelatihan(Request $req)
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
                        "ijazah" => $peserta->ijazah,
                        "usia" => $usia,
                    ],
                    "pelatihan" => $pelatihan,
                ];
            }
        }
        $pendaftaran = $temp_pendaftaran;


        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function tolakPendaftaran(Request $req)
    {
        $pendaftaran = PendaftaranPelatihan::find($req->pp_id);
        if($pendaftaran->status_pendaftaran == 0 || $pendaftaran->status_pendaftaran == 1){
            $pendaftaran->status_kelulusan = 2;
        }
        else{
            $pendaftaran->status_pendaftaran = 4;
            $pendaftaran->status_kelulusan = 2;
        }
        $pendaftaran->save();
        $temp_pendaftaran = [];
        $temp_pendaftaran[] = $pendaftaran;
        $pendaftaran = $temp_pendaftaran;

        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil update"
        ], 201);
    }

    public function terimaPendaftaran(Request $req)
    {
        $pendaftaran = PendaftaranPelatihan::find($req->pp_id);
        if($pendaftaran->status_pendaftaran == 0 || $pendaftaran->status_pendaftaran == 1){
            if($pendaftaran->status_pendaftaran == 0){
                $pendaftaran->tgl_wawancara = $req->tgl_wawancara;
            }
            $pendaftaran->status_pendaftaran += 1;
        }
        else{
            $pendaftaran->status_pendaftaran += 1;
            $pendaftaran->status_kelulusan = 1;
        }
        $pendaftaran->save();
        $temp_pendaftaran = [];
        $temp_pendaftaran[] = $pendaftaran;
        $pendaftaran = $temp_pendaftaran;

        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil update"
        ], 201);
    }

    public function getAllPeserta(Request $req)
    {
        $users = User::where("role",0)->get();
        $userRet = [];
        foreach($users as $user){
            $peserta = $user->peserta;
            $dob = date_create($peserta->tgl_lahir);

            $now = new DateTime(now());
            $tgl_lahir = new DateTime($peserta->tgl_lahir);
            $usia = $now->diff($tgl_lahir)->format("%Y");

            $temp = [
                "user_id" => $user->user_id,
                "nama" => $user->nama,
                "email" => $user->email,
                "username" => $user->username,
                "password" => $user->password,
                "telp" => $user->telp,
                "role" => $user->role,
                "peserta_id" => $peserta->peserta_id,
                "nik" => $peserta->nik,
                "tgl_lahir" => date_format($dob, "d/m/Y"),
                "pendidikan" => $peserta->pendidikan->nama,
                "jurusan" => $peserta->jurusan,
                "nilai" => $peserta->nilai,
                "status" => $peserta->status,
                "ijazah" => $peserta->ijazah,
                "usia" => $usia,
            ];
            $userRet[] = $temp;
        }

        return response()->json([
            "userResponse" => $userRet,
            "message" => "Berhasil fetch user",
            "status" => 1
        ], 200);
    }

    public function getAllPerusahaan(Request $req)
    {
        $users = User::where("role",1)->get();
        $userRet = [];
        foreach($users as $user){
            $perusahaan = $user->perusahaan;


            $temp = Lowongan::with('syarat')->where("perusahaan_id",$user->perusahaan->perusahaan_id)->where("status","!=",0)->get();
            $lowongan = [];

            foreach($temp as $t){
                $lowongan[] = [
                    "lowongan_id" => $t->lowongan_id,
                    "nama" => $t->nama,
                    "kategori" => $t->kategori->nama,
                    "perusahaan" => $t->perusahaan->user->nama,
                    "kuota" => $t->kuota,
                    "keterangan" => $t->keterangan,
                    "status" => $t->status,
                    "syarat" => $t->syarat
                ];
            }

            $temp = [
                "user_id" => $user->user_id,
                "nama" => $user->nama,
                "email" => $user->email,
                "username" => $user->username,
                "password" => $user->password,
                "telp" => $user->telp,
                "role" => $user->role,
                "perusahaan_id" => $perusahaan->perusahaan_id,
                "alamat" => $perusahaan->alamat,
                "listLowongan" => $lowongan,
            ];
            $userRet[] = $temp;
        }

        return response()->json([
            "userResponse" => $userRet,
            "message" => "Berhasil fetch user",
            "status" => 1
        ], 200);
    }
}
