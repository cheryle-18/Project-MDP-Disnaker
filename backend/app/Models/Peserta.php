<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Peserta extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "peserta";
    protected $primaryKey = "peserta_id";
    public $incrementing = true;
    public $timestamps = true;

    public function user(){
        return $this->belongsTo(User::class, 'user_id', 'user_id');
    }

    public function pelatihan(){
        return $this->belongsToMany(Pelatihan::class, 'pendaftaran_pelatihan', 'peserta_id', 'pelatihan_id')->withPivot("pp_id", "status_pendaftaran", "status_kelulusan");
    }

    public function lowongan(){
        return $this->belongsToMany(Lowongan::class, 'pendaftaran_lowongan', 'peserta_id', 'lowongan_id');
    }

    public function pendaftaranPelatihan(){
        return $this->hasMany(PendaftaranPelatihan::class, 'peserta_id', 'peserta_id');
    }

    public function pendaftaranLowongan(){
        return $this->hasMany(pendaftaranLowongan::class, 'peserta_id', 'peserta_id');
    }
}
