<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Kategori extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "kategori";
    protected $primaryKey = "kategori_id";
    public $incrementing = true;
    public $timestamps = true;

    public function pelatihan(){
        return $this->hasMany(Pelatihan::class, 'kategori_id', 'kategori_id');
    }

    public function lowongan(){
        return $this->hasMany(Lowongan::class, 'kategori_id', 'kategori_id');
    }
}
