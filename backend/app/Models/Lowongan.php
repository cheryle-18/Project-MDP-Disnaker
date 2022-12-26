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
    public $timestamps = false;

    public function peserta(){
        return $this->belongsToMany(Peserta::class, 'pendaftaran_lowongan', 'lowongan_id', 'peserta_id');
    }

    public function kategori(){
        return $this->belongsTo(Kategori::class, 'kategori_id', 'kategori_id');
    }

    public function syarat(){
        return $this->hasMany(SyaratPelatihan::class, 'lowongan_id', 'lowongan_id');
    }
}
