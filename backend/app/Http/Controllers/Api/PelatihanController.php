<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use App\Models\Pelatihan;
use App\Models\PendaftaranPelatihan;
use App\Models\SyaratPelatihan;
use Illuminate\Http\Request;

class PelatihanController extends Controller
{
    //
    public function getAllPelatihan(){
        $temp = Pelatihan::all();
        $pelatihan = [];

        foreach($temp as $t){
            $pelatihan[] = [
                "pelatihan_id" => $t->pelatihan_id,
                "nama" => $t->nama,
                "kategori" => $t->kategori->nama,
                "kuota" => $t->kuota,
                "durasi"=>$t->durasi,
                "pendidikan"=>$t->pendidikan,
                "keterangan" => $t->keterangan,
                "status" => $t->status,
                "syarat" => $t->syarat
            ];
        }

        return response()->json([
            "pelatihan" => $pelatihan,
            "message" => "Berhasil fetch"
        ], 200);
    }


    public function insertPelatihan(Request $req){
        //get kategori id
        $kategori = Kategori::where('nama', $req->kategori)->first();

        $pelatihan = new Pelatihan();
        $pelatihan->nama = $req->nama;
        $pelatihan->kategori_id = $kategori->kategori_id;
        $pelatihan->kuota = $req->kuota;
        $pelatihan->durasi = $req->durasi;
        $pelatihan->pendidikan = $req->pendidikan;
        $pelatihan->keterangan = $req->keterangan;
        $pelatihan->status = 1;
        $pelatihan->save();

        //insert syarat
        $syarat = $req->syarat;
        if(sizeof($syarat)>0){
            foreach($syarat as $s){
                SyaratPelatihan::create([
                    'pelatihan_id' => $pelatihan->pelatihan_id,
                    'deskripsi' => $s["deskripsi"]
                ]);
            }
        }

        return response()->json([
            "pelatihan" => [$pelatihan],
            "message" => "Berhasil menambah pelatihan"
        ], 201);
    }

    public function getPendaftaran(Request $req){
        $temp = PendaftaranPelatihan::where('pelatihan_id', $req->pelatihan_id)->get();
        $pendaftaran = [];

        if(sizeof($temp)>0){
            foreach($temp as $t){
                $tgl = date_create($t->tanggal);
                $dob = date_create($t->peserta->tgl_lahir);
                $pendaftaran[] = [
                    "pp_id" => $t->pp_id,
                    "status_pendaftaran" => $t->status_pendaftaran,
                    "status_kelulusan" => $t->status_kelulusan,
                    "tanggal" => date_format($tgl, "d F Y"),
                    "peserta_id" => $t->peserta->peserta_id,
                    "nama" => $t->peserta->user->nama,
                    "email" => $t->peserta->user->email,
                    "telp" => $t->peserta->user->telp,
                    "tgl_lahir" => date_format($dob, "d F Y"),
                    "usia" => date_diff($dob, date_create('now'))->y,
                    "pendidikan" => $t->peserta->pendidikan,
                    "jurusan" => $t->peserta->jurusan,
                    "nilai" => $t->peserta->nilai
                ];
            }
        }

        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil fetch"
        ], 200);
    }
}