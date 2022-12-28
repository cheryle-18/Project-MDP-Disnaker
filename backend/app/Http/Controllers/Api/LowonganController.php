<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use App\Models\Lowongan;
use App\Models\PendaftaranLowongan;
use App\Models\SyaratLowongan;
use Illuminate\Http\Request;

class LowonganController extends Controller
{
    public function getAllLowongan(){
        $lowongan = Lowongan::with('syarat')->get();

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function getLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id)->with('syarat');

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function insertLowongan(Request $req){
        //get kategori id
        $kategori = Kategori::where('nama', $req->kategori);

        $lowongan = new Lowongan();
        $lowongan->nama = $req->nama;
        $lowongan->kategori_id = $kategori->kategori_id;
        $lowongan->perusahaan_id = $req->perusahaan_id;
        $lowongan->kuota = $req->kuota;
        $lowongan->keterangan = $req->keterangan;
        $lowongan->status = $req->status;
        $lowongan->save();

        //insert syarat
        $syarat = $req->syarat;
        foreach($syarat as $s){
            SyaratLowongan::create([
                'lowongan_id' => $lowongan->lowongan_id,
                'deskripsi' => $s->deskripsi
            ]);
        }

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil menambah lowongan"
        ], 201);
    }

    public function updateLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);
        $kategori = Kategori::where('nama', $req->kategori);

        $lowongan->nama = $req->nama;
        $lowongan->kategori_id = $kategori->kategori_id;
        $lowongan->kuota = $req->kuota;
        $lowongan->keterangan = $req->keterangan;
        $lowongan->status = $req->status;
        $lowongan->save();

        //delete all syarat
        SyaratLowongan::where('lowongan_id', $req->lowongan_id)->delete();

        //insert syarat
        $syarat = $req->syarat;
        foreach($syarat as $s){
            SyaratLowongan::create([
                'lowongan_id' => $lowongan->lowongan_id,
                'deskripsi' => $s->deskripsi
            ]);
        }

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil mengubah lowongan"
        ], 200);
    }

    public function deleteLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);

        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id);
        if(!$pendaftaran){ //blm ada yg daftar
            //delete syarat
            SyaratLowongan::where('lowongan_id', $req->lowongan_id)->delete();

            //delete lowongan
            $lowongan->delete();

            return response()->json([
                "lowongan" => $lowongan,
                "message" => "Berhasil menghapus lowongan"
            ], 200, );
        }
    }

    public function tutupLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);
        $lowongan->status = 0;
        $lowongan->save();

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil menutup lowongan"
        ], 200);
    }

    public function getPendaftaran(Request $req){
        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id);

        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function daftarLowongan(Request $req){

    }
}
