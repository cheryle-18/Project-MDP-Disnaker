<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Pelatihan;
use App\Models\PendaftaranLowongan;
use App\Models\PendaftaranPelatihan;
use App\Models\Pendidikan;
use App\Models\Perusahaan;
use App\Models\Peserta;
use App\Models\User;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

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

    public function getAllPerusahaan(){
        $t = Perusahaan::all();
        $perusahaan = [];

        if(sizeof($t)>0){
            foreach($t as $temp){
                $perusahaan[] = [
                    "perusahaan_id" => $temp->perusahaan_id,
                    "nama" => $temp->user->nama,
                    "email" => $temp->user->email,
                    "telp" => $temp->user->telp,
                    "alamat" => $temp->alamat
                ];
            }
        }

        return response()->json([
            "perusahaan" => $perusahaan,
            "message" => "Berhasil fetch perusahaan"
        ], 200);
    }

    public function getPerusahaan(Request $req){
        $temp = Perusahaan::find($req->perusahaan_id);
        $perusahaan = [
            "perusahaan_id" => $temp->perusahaan_id,
            "nama" => $temp->user->nama,
            "email" => $temp->user->email,
            "telp" => $temp->user->telp,
            "alamat" => $temp->alamat
        ];

        return response()->json([
            "perusahaan" => [$perusahaan],
            "message" => "Berhasil fetch perusahaan"
        ], 200);
    }

    public function insertPerusahaan(Request $req){
        //create user
        $user = new User();
        $user->nama = $req->nama;
        $user->username = $req->username;
        $user->password = $req->password;
        $user->role = 1;
        $user->email = $req->email;
        $user->telp = $req->telp;
        $user->save();

        //create perusahaan
        $perusahaan = new Perusahaan();
        $perusahaan->user_id = $user->user_id;
        $perusahaan->alamat = $req->alamat;
        $perusahaan->save();

        return response()->json([
            "message" => "Berhasil menambah perusahaan"
        ], 201);
    }

    public function updatePerusahaan(Request $req){
        $perusahaan = Perusahaan::find($req->perusahaan_id);
        $perusahaan->alamat = $req->alamat;
        $perusahaan->save();

        $user = User::find($perusahaan->user_id);
        $user->email = $req->email;
        $user->telp = $req->telp;
        $user->save();

        return response()->json([
            "message" => "Berhasil mengubah profile perusahaan"
        ], 200);
    }

    public function updatePasswordPerusahaan(Request $req){
        $perusahaan = Perusahaan::find($req->perusahaan_id);
        $status = 0;
        $message = "";
        $user = User::find($perusahaan->user_id);

        if(hash::check( $req->passbaru, $user->password)){
            $message = "Password tidak boleh sama dengan password sebelumnya";
        }
        else if(!hash::check($req->passlama, $user->password)){
            $message = "Password lama salah";
        }
        else{
            $user->password = Hash::make($req->passbaru);
            $user->save();
            $message = "Berhasil mengubah password";
            $status = 1;
        }

        return response()->json([
            "status" => $status,
            "message" => $message
        ], 200);
    }

    public function getAllPeserta(){
        $t = Peserta::all();
        $peserta = [];

        if(sizeof($t)>0){
            foreach($t as $temp){
                $peserta[] = [
                    "peserta_id" => $temp->peserta_id,
                    "nama" => $temp->user->nama,
                    "nik" => $temp->nik,
                    "email" => $temp->user->email,
                    "telp" => $temp->user->telp,
                    "tgl_lahir" => date_format(date_create($temp->tgl_lahir), "d/m/Y"),
                    "pendidikan" => $temp->pendidikan->nama,
                    "jurusan" => $temp->jurusan,
                    "nilai" => $temp->nilai
                ];
            }
        }

        return response()->json([
            "peserta" => $peserta,
            "message" => "Berhasil fetch peserta"
        ], 200);
    }

    public function getPeserta(Request $req){
        $temp = Peserta::find($req->peserta_id)->first();
        $peserta = [
            "peserta_id" => $temp->peserta_id,
            "nama" => $temp->user->nama,
            "nik" => $temp->nik,
            "email" => $temp->user->email,
            "telp" => $temp->user->telp,
            "tgl_lahir" => date_format(date_create($temp->tgl_lahir), "d/m/Y"),
            "pendidikan" => $temp->pendidikan->nama,
            "jurusan" => $temp->jurusan,
            "nilai" => $temp->nilai
        ];

        return response()->json([
            "peserta" => [$peserta],
            "message" => "Berhasil fetch peserta"
        ], 200);
    }

    public function updatePeserta(Request $req){
        $peserta = Peserta::find($req->peserta_id);
        $peserta->tgl_lahir = date_format(date_create($req->tgl_lahir, ), 'Y-m-d');
        $peserta->nik = $req->nik;
        $peserta->save();

        $user = User::find($peserta->user_id);
        $user->email = $req->email;
        $user->telp = $req->telp;
        $user->save();

        return response()->json([
            "message" => "Berhasil edit profil peserta"
        ], 200);
    }


    public function updatePasswordPeserta(Request $req){
        $peserta = Peserta::find($req->peserta_id);
        $message = "";
        $status = 0;
        $user = User::find($peserta->user_id);

        if(hash::check( $req->passbaru, $user->password)){
            $message = "Password tidak boleh sama dengan password sebelumnya";
        }
        else if(!hash::check($req->passlama, $user->password)){
            $message = "Password lama salah";
        }
        else{
            $user->password = Hash::make($req->passbaru);
            $user->save();
            $message = "Berhasil mengubah password";
            $status = 1;
        }

        return response()->json([
            "status" => $status,
            "message" => $message
        ], 200);
    }

    public function updatePendidikan(Request $req){
        $peserta = Peserta::find($req->peserta_id);
        $pendidikan = Pendidikan::where('nama', $req->pendidikan)->first();

        $peserta->jurusan = $req->jurusan;
        $peserta->pendidikan_id = $pendidikan->pendidikan_id;
        $peserta->nilai = $req->nilai;
        $peserta->save();

        return response()->json([
            "message" => "Berhasil mengubah riwayat pendidikan."
        ], 200);
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
        $peserta = Peserta::find($req->peserta_id);
        $peserta->status = 0;
        $peserta->save();

        return response()->json([
            "message" => "Berhasil mengubah Status Kerja"
        ], 200);
    }

    public function getPendaftaran(Request $req){
        $temp = PendaftaranPelatihan::where('peserta_id', $req->peserta_id)->get();
        $pelatihan = [];
        $message = "";

        if(sizeof($temp)>0){
            foreach($temp as $t){
                $pelatihan[] = [
                    "pelatihan_id" => $t->pelatihan_id,
                    "pelatihan" => $t->pelatihan->nama,
                    "peserta" => $t->peserta->nama,
                    "tgl_pendaftaran" => date_format(date_create($t->tgl_pendaftaran), "d M Y"),
                    "tgl_wawancara" => date_format(date_create($t->tgl_wawancara), "d M Y"),
                    "status_pendaftaran" => $t->status_pendaftaran,
                    "status_kelulusan" => $t->status_kelulusan,
                ];
            }
            $message = "Berhasil fetch";
        }
        else{
            $message = "Anda belum mendaftar pelatihan apapun.";
        }

        return response()->json([
            "pendaftaran" => $pelatihan,
            "message" => $message
        ], 200);
    }

    function daftarPelatihan(Request $req)
    {
        # code...
        $peserta = Peserta::find($req->peserta_id);
        $pelatihan = Pelatihan::find($req->pelatihan_id);

        //check for peserta status

        if($peserta->status == 1){
            return response()->json([
                "message" => "Anda sedang melakukan pelatihan!"
            ], 200);
        }
        //check for pendidikan
        if($peserta->pendidikan_id < $pelatihan->pendidikan_id){
            return response()->json([
                "message" => "Anda belum memenuhi pendidikan minimal pelatihan ini!"
            ], 200);
        }

        //daftar
        PendaftaranPelatihan::create([
            "pelatihan_id" => $req->pelatihan_id,
            "peserta_id" => $req->peserta_id,
            "tgl_pendaftaran" => Carbon::now('Asia/Jakarta'),
            "status_pendaftaran" => 0,
            "status_kelulusan" => 0
        ]);

        //auto tutup pelatihan kl jmlh pendaftaran = kuota
        $pendaftaran = PendaftaranPelatihan::where('pelatihan_id', $req->pelatihan_id)->count();
        if($pelatihan->kuota - $pendaftaran <= 0){
            $pelatihan->status = 0;
            $pelatihan->save();
        }

        //update status peserta jd sedang pelatihan
        $peserta->status = 1;
        $peserta->save();

        return response()->json([
            "message" => "1"
        ], 200);
     }


}

