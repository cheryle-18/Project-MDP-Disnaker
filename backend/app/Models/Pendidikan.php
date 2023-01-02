<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Pendidikan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "pendidikan";
    protected $primaryKey = "pendidikan_id";
    public $incrementing = true;
    public $timestamps = true;

    protected $fillable = [
        "pendidikan_id",
        "nama",
    ];

    public function peserta(){
        return $this->hasMany(Peserta::class, 'pendidikan_id', 'pendidikan_id');
    }

    public function pelatihan(){
        return $this->hasMany(Pelatihan::class, 'pendidikan', 'pendidikan_id');
    }
}
