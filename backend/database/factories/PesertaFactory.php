<?php

namespace Database\Factories;

use App\Models\Pendidikan;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Arr;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Peserta>
 */
class PesertaFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        $pendidikan_id = $this->faker->randomElement(Pendidikan::all()->pluck('pendidikan_id'));
        $jurusan = '-';
        $status = $this->faker->numberBetween(0,2);
        $nilai = null;
        if($pendidikan_id == 4){
            $jurusan = $this->faker->randomElement(['IPA','IPS','Bahasa']);
        }
        else if($pendidikan_id > 4){
            $jurusan = $this->faker->randomElement(['Teknik Elektro','Teknik Informatika','Manajemen Bisnis','Teknik Konstruksi','Desain Interior','Digital Arsitektur','Desain Produk','Desain Komunikasi Visual','Teknik Broadcasting','Sastra Inggris','Akuntansi','Teknik Mesin','Teknik Sipil','Pendidikan Bahasa Mandarin','Manajemen Pajak','Perbankan dan Keuangan']);
        }

        if($status == 2){
            $nilai = $this->faker->numberBetween(40,100);
        }

        return [
            'user_id' => $this->faker->unique()->randomElement(User::where('role','=',0)->pluck('user_id')),
            'nik' => '1234567890',
            'tgl_lahir' => $this->faker->dateTimeBetween('1995-01-01', '2002-12-20'),
            'pendidikan_id' => $pendidikan_id,
            'jurusan' => $jurusan,
            'nilai' => $nilai,
            'status' => $status,
        ];
    }
}
