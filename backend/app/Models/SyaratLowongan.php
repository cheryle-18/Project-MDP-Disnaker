<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class SyaratLowongan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "syarat_lowongan";
    protected $primaryKey = "sl_id";
    public $incrementing = true;
    public $timestamps = false;

    protected $fillable = [
        'sl_id',
        'lowongan_id',
        'deskripsi',
        'created_at',
        'updated_at'
    ];

    public function lowongan(){
        return $this->belongsTo(Lowongan::class, 'lowongan_id', 'lowongan_id');
    }
}
