<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class PendaftaranPelatihan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "pendaftaran_pelatihan";
    protected $primaryKey = "pp_id";
    public $incrementing = true;
    public $timestamps = true;

    protected $fillable = [
        'pp_id',
        'pelatihan_id',
        'peserta_id',
        'tgl_pendaftaran',
        'tgl_wawancara',
        'status_pendaftaran',
        'status_kelulusan'
    ];

    public function pelatihan(){
        return $this->belongsTo(Pelatihan::class, 'pelatihan_id', 'pelatihan_id');
    }

    public function peserta(){
        return $this->belongsTo(Peserta::class, 'peserta_id', 'peserta_id');
    }
}
