<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class SyaratPelatihan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "syarat_pelatihan";
    protected $primaryKey = "sp_id";
    public $incrementing = true;
    public $timestamps = true;

    public function pelatihan(){
        return $this->belongsTo(Pelatihan::class, 'pelatihan_id', 'pelatihan_id');
    }
}
