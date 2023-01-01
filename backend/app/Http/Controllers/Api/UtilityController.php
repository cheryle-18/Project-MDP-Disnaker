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

    public function getPeserta(Request $req){
        $temp = Peserta::find($req->peserta_id);
        $peserta = [
            "peserta_id" => $temp->peserta_id,
            "nama" => $temp->user->nama,
            "nik" => $temp->nik,
            "email" => $temp->user->email,
            "telp" => $temp->user->telp,
            "tgl_lahir" => date_format(date_create($temp->tgl_lahir), "d F Y"),
            "pendidikan" => $temp->pendidikan,
            "jurusan" => $temp->jurusan,
            "nilai" => $temp->nilai
        ];

        return response()->json([
            "peserta" => $peserta,
            "message" => "Berhasil fetch peserta"
        ], 200);
    }

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
                    "tgl_lahir" => date_format($dob, "d F Y"),
                    "pendidikan" => $peserta->pendidikan,
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
}
