<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Pelatihan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "pelatihan";
    protected $primaryKey = "pelatihan_id";
    public $incrementing = true;
    public $timestamps = true;

    public function peserta(){
        return $this->belongsToMany(Peserta::class, 'pendaftaran_pelatihan', 'pelatihan_id', 'peserta_id')->withPivot("pp_id", "status_pendaftaran", "status_kelulusan", "tgl_pendaftaran", "tgl_wawancara");
    }

    public function kategori(){
        return $this->belongsTo(Kategori::class, 'kategori_id', 'kategori_id');
    }

    public function pendidikan(){
        return $this->belongsTo(Pendidikan::class, 'pendidikan_id', 'pendidikan_id');
    }

    public function syarat(){
        return $this->hasMany(SyaratPelatihan::class, 'pelatihan_id', 'pelatihan_id');
    }
}
