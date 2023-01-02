<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\PendaftaranLowongan;
use App\Models\PendaftaranPelatihan;
use App\Models\Perusahaan;
use App\Models\Peserta;
use App\Models\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    function getUsers()
    {
        $users = User::all();
        $userRet = [];
        foreach($users as $user){
            if($user->role==0){
                $peserta = $user->peserta;
                $dob = date_create($peserta->tgl_laihr);
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
                ];
            }
            else if($user->role==1){
                $perusahaan = $user->perusahaan;
                $temp = [
                    "user_id" => $user->user_id,
                    "nama" => $user->nama,
                    "email" => $user->email,
                    "username" => $user->username,
                    "password" => $user->password,
                    "telp" => $user->telp,
                    "role" => $user->role,
                    "perusahaan_id" => $perusahaan->perusahaan_id,
                    "alamat" => $perusahaan->alamat
                ];
            }
            $userRet[] = $temp;
        }

        return response()->json([
            "userResponse" => $userRet,
            "message" => "Berhasil fetch user",
            "status" => 1
        ], 200);
    }

    public function getAllPerusahaan(Request $req){

    }

    public function getPerusahaan(Request $req){
        $temp = Perusahaan::where('user_id', $req->user_id)->first();
        $perusahaan = [
            "perusahaan_id" => $temp->perusahaan_id,
            "nama" => $temp->user->nama,
            "email" => $temp->user->email,
            "telp" => $temp->user->telp,
            "alamat" => $temp->alamat
        ];

        return response()->json([
            "perusahaan" => $perusahaan,
            "message" => "Berhasil fetch perusahaan"
        ], 200);
    }

    public function updatePerusahaan(Request $req){

    }

    public function getAllPeserta(Request $req){

    }

    public function getPeserta(Request $req){
        $temp = Peserta::where('user_id', $req->user_id)->first();
        $peserta = [
            "peserta_id" => $temp->peserta_id,
            "nama" => $temp->user->nama,
            "nik" => $temp->nik,
            "email" => $temp->user->email,
            "telp" => $temp->user->telp,
            "tgl_lahir" => date_format(date_create($temp->tgl_lahir), "d F Y"),
            "pendidikan" => $temp->pendidikan->nama,
            "jurusan" => $temp->jurusan,
            "nilai" => $temp->nilai
        ];

        return response()->json([
            "peserta" => $peserta,
            "message" => "Berhasil fetch peserta"
        ], 200);
    }

    public function updatePeserta(Request $req){

    }

    public function updatePendidikan(Request $req){

    }

    public function getRiwayatPelatihan(Request $req){
        $pendaftaran = PendaftaranPelatihan::where('peserta_id', $req->peserta_id)->where('status_pendaftaran', 3)->where('status_kelulusan', 1)->get();

        $pelatihan = [];
        $message = "";

        if(sizeof($pendaftaran)>0){
            foreach($pendaftaran as $p){
                $pelatihan[] = [
                    "nama" => $p->pelatihan->nama,
                    "kategori" => $p->pelatihan->kategori->nama,
                    "tanggal" => date_format(date_create($p->tgl_pendaftaran), "d F Y")
                ];
            }

            $message = "Berhasil fetch";
        }
        else{
            $message = "Anda tidak memiliki riwayat pelatihan.";
        }

        return response()->json([
            "riwayat" => $pelatihan,
            "message" => $message
        ], 200);
    }

    public function getRiwayatPekerjaan(Request $req){
        $pendaftaran = PendaftaranLowongan::where('peserta_id', $req->peserta_id)->get();

        $lowongan = [];
        $message = "";

        if(sizeof($pendaftaran)>0){
            foreach($pendaftaran as $p){
                $lowongan[] = [
                    "nama" => $p->lowongan->nama,
                    "perusahaan" => $p->lowongan->perusahaan->user->nama,
                    "kategori" => $p->lowongan->kategori->nama,
                    "tanggal" => date_format(date_create($p->tanggal), "d F Y")
                ];
            }

            $message = "Berhasil fetch";
        }
        else{
            $message = "Anda tidak memiliki riwayat pekerjaan.";
        }

        return response()->json([
            "riwayat" => $lowongan,
            "message" => $message
        ], 200);
    }

    public function updateStatusKerja(Request $req){

    }

    public function getPendaftaran(Request $req){
        $temp = PendaftaranPelatihan::where('peserta_id', $req->peserta_id)->get();
        $pelatihan = [];
        $message = "";

        if(sizeof($temp)>0){
            foreach($temp as $t){
                $pelatihan[] = [
                    "pelatihan_id" => $t->pelatihan_id,
                    "nama" => $t->pelatihan->nama,
                    "tgl_pendaftaran" => date_format(date_create($t->tgl_pendaftaran), "d F Y"),
                    "tgl_wawancara" => date_format(date_create($t->tgl_wawancara), "d F Y"),
                    "status_pendaftaran" => $t->status_pendaftaran,
                    "status_kelulusan" => $t->status_kelulusan
                ];
            }
            $message = "Berhasil fetch";
        }
        else{
            $message = "Anda belum mengikuti pelatihan apapun.";
        }

        return response()->json([
            "pendaftaran" => $pelatihan,
            "message" => $message
        ], 200);
    }
}
