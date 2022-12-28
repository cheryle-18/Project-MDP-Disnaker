<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Perusahaan extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $table = "perusahaan";
    protected $primaryKey = "perusahaan_id";
    public $incrementing = true;
    public $timestamps = true;

    public function user(){
        return $this->belongsTo(User::class, 'user_id', 'user_id');
    }

    public function lowongan(){
        return $this->hasMany(Lowongan::class, 'perusahaan_id', 'perusahaan_id');
    }
}
