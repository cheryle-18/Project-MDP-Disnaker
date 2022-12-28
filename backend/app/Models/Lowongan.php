<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Lowongan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "lowongan";
    protected $primaryKey = "lowongan_id";
    public $incrementing = true;
    public $timestamps = true;

    protected $fillable = [
        'lowongan_id',
        'nama',
        'kategori_id',
        'perusahaan_id',
        'kuota',
        'keterangan',
        'status',
        'created_at',
        'updated_at'
    ];

    public function perusahaan(){
        return $this->belongsTo(Perusahaan::class, 'perusahaan_id', 'perusahaan_id');
    }

    public function peserta(){
        return $this->belongsToMany(Peserta::class, 'pendaftaran_lowongan', 'lowongan_id', 'peserta_id');
    }

    public function kategori(){
        return $this->belongsTo(Kategori::class, 'kategori_id', 'kategori_id');
    }

    public function syarat(){
        return $this->hasMany(SyaratLowongan::class, 'lowongan_id', 'lowongan_id');
    }
}
