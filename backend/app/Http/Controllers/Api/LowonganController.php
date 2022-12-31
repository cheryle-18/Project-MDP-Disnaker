<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use App\Models\Lowongan;
use App\Models\PendaftaranLowongan;
use App\Models\PendaftaranPelatihan;
use App\Models\Perusahaan;
use App\Models\Peserta;
use App\Models\SyaratLowongan;
use Illuminate\Http\Request;

class LowonganController extends Controller
{
    public function getAllLowongan(){
        $temp = Lowongan::with('syarat')->get();
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

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function getLowonganPerusahaan(Request $req){
        $temp = Lowongan::where('perusahaan_id', $req->perusahaan_id)->with('syarat')->get();
        $lowongan = [];

        foreach($temp as $t){
            $pendaftaran = PendaftaranLowongan::where('lowongan_id', $t->lowongan_id)->count();

            $lowongan[] = [
                "lowongan_id" => $t->lowongan_id,
                "nama" => $t->nama,
                "kategori" => $t->kategori->nama,
                "perusahaan" => $t->perusahaan->user->nama,
                "kuota" => $t->kuota,
                "pendaftaran" => $pendaftaran,
                "keterangan" => $t->keterangan,
                "status" => $t->status,
                "syarat" => $t->syarat
            ];
        }

        return response()->json([
            "lowongan" => $lowongan,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function getLowonganPeserta(Request $req){
        $peserta = Peserta::find($req->peserta_id);
        $message = "";
        $lowongan = [];

        //cek sdh kerja blm
        if($peserta->status==2){
            $message = "Anda sudah memiliki pekerjaan saat ini.";
        }
        else{
            //find pelatihan selesai
            $pelatihan = PendaftaranPelatihan::where('peserta_id', $req->peserta_id)->where('status_pendaftaran', 3)->where('status_kelulusan', 1)->get();

            if(sizeof($pelatihan)>0){ //sdh selesai pelatihan
                foreach($pelatihan as $p){
                    $low = Lowongan::where('kategori_id', $p->pelatihan->kategori_id)->where('status', 1)->get();
                    if(sizeof($low)>0){
                        foreach($low as $t){
                            $lowongan[] = [
                                "lowongan_id" => $t->lowongan_id,
                                "nama" => $t->nama,
                                "kategori" => $t->kategori->nama,
                                "perusahaan" => $t->perusahaan->user->nama,
                                "kuota" => $t->kuota,
                                "keterangan" => $t->keterangan,
                                "status" => $t->status
                            ];
                        }
                    }
                }

                $message = "Berhasil fetch.";
            }
            else{ //blm ada pelatihan selesai
                $message = "Anda belum menyelesaikan pelatihan apapun.\n\nAnda dapat melihat lowongan yang tersedia setelah menyelesaikan minimal satu pelatihan.";
            }
        }

        return response()->json([
            "lowongan" => $lowongan,
            "message" => $message
        ], 200);
    }

    public function getLowongan(Request $req){
        $t = Lowongan::where('lowongan_id', $req->lowongan_id)->with('syarat')->first();
        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id)->count();

        $lowongan = [
            "lowongan_id" => $t->lowongan_id,
            "nama" => $t->nama,
            "kategori" => $t->kategori->nama,
            "perusahaan" => $t->perusahaan->user->nama,
            "kuota" => $t->kuota,
            "pendaftaran" => $pendaftaran,
            "keterangan" => $t->keterangan,
            "status" => $t->status,
            "syarat" => $t->syarat
        ];

        return response()->json([
            "currLowongan" => $lowongan,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function insertLowongan(Request $req){
        //get kategori id
        $kategori = Kategori::where('nama', $req->kategori)->first();

        $lowongan = new Lowongan();
        $lowongan->nama = $req->nama;
        $lowongan->kategori_id = $kategori->kategori_id;
        $lowongan->perusahaan_id = $req->perusahaan_id;
        $lowongan->kuota = $req->kuota;
        $lowongan->keterangan = $req->keterangan;
        $lowongan->status = 1;
        $lowongan->save();

        //insert syarat
        $syarat = $req->syarat;
        if(sizeof($syarat)>0){
            foreach($syarat as $s){
                SyaratLowongan::create([
                    'lowongan_id' => $lowongan->lowongan_id,
                    'deskripsi' => $s["deskripsi"]
                ]);
            }
        }

        return response()->json([
            // "lowongan" => $lowongan,
            "message" => "Berhasil menambah lowongan"
        ], 201);
    }

    public function updateLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);
        $kategori = Kategori::where('nama', $req->kategori)->first();

        $lowongan->nama = $req->nama;
        $lowongan->kategori_id = $kategori->kategori_id;
        $lowongan->kuota = $req->kuota;
        $lowongan->keterangan = $req->keterangan;
        $lowongan->save();

        //delete all syarat
        SyaratLowongan::where('lowongan_id', $req->lowongan_id)->delete();

        //insert syarat
        $syarat = $req->syarat;
        if(sizeof($syarat)>0){
            foreach($syarat as $s){
                SyaratLowongan::create([
                    'lowongan_id' => $lowongan->lowongan_id,
                    'deskripsi' => $s["deskripsi"]
                ]);
            }
        }

        return response()->json([
            // "lowongan" => $lowongan,
            "message" => "Berhasil mengubah lowongan"
        ], 200);
    }

    public function deleteLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);

        $pendaftaran = [];
        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id)->get();
        if(sizeof($pendaftaran)==0){ //blm ada yg daftar
            //delete syarat
            SyaratLowongan::where('lowongan_id', $req->lowongan_id)->delete();

            //delete lowongan
            $lowongan->delete();

            return response()->json([
                // "lowongan" => $lowongan,
                "message" => "Berhasil menghapus lowongan"
            ], 200);
        }
        else{
            return response()->json([
                // "lowongan" => $lowongan,
                "message" => "Lowongan tidak bisa dihapus"
            ], 500);
        }
    }

    public function tutupLowongan(Request $req){
        $lowongan = Lowongan::find($req->lowongan_id);
        $lowongan->status = !$lowongan->status;
        $lowongan->save();

        return response()->json([
            // "lowongan" => $lowongan,
            "message" => "Berhasil menutup lowongan"
        ], 200);
    }

    public function getPendaftaran(Request $req){
        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id)->with('peserta')->get();

        return response()->json([
            "pendaftaran" => $pendaftaran,
            "message" => "Berhasil fetch"
        ], 200);
    }

    public function daftarLowongan(Request $req){
        $peserta = Peserta::find($req->peserta_id);
        $lowongan = Lowongan::find($req->lowongan_id);

        //daftar
        PendaftaranLowongan::create([
            "lowongan_id" => $req->lowongan_id,
            "peserta_id" => $req->peserta_id,
            "tanggal" => date("Y-m-d")
        ]);

        //auto tutup lowongan kl jmlh pendaftaran = kuota
        $pendaftaran = PendaftaranLowongan::where('lowongan_id', $req->lowongan_id)->count();
        if($lowongan->kuota - $pendaftaran <= 0){
            $lowongan->status = 0;
            $lowongan->save();
        }

        //update status peserta jd sdh kerja
        $peserta->status = 2;
        $peserta->save();

        return response()->json([
            "message" => "Berhasil daftar lowongan"
        ], 200);
    }
}
