<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class PendaftaranLowongan extends Model
{
    use HasFactory;

    protected $table = "pendaftaran_lowongan";
    protected $primaryKey = "pl_id";
    public $incrementing = true;
    public $timestamps = false;

    public function lowongan(){
        return $this->belongsTo(Lowongan::class, 'lowongan_id', 'lowongan_id');
    }

    public function peserta(){
        return $this->belongsTo(Peserta::class, 'peserta_id', 'peserta_id');
    }
}
