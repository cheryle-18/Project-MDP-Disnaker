<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use HasFactory;

    protected $table = "user";
    protected $primaryKey = "user_id";
    public $incrementing = true;
    public $timestamps = false;

    protected $fillable = [
        "nama",
        "username",
        "email",
        "password",
        "telp",
        "role"
    ];

    public function peserta(){
        return $this->hasMany(Peserta::class, 'user_id', 'user_id');
    }

    public function perusahaan(){
        return $this->hasMany(Perusahaan::class, 'user_id', 'user_id');
    }
}
