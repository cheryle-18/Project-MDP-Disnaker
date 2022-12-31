<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use App\Models\Pelatihan;
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
}
