<?php

namespace Database\Factories;

use App\Models\Kategori;
use App\Models\Perusahaan;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Lowongan>
 */
class LowonganFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    private static $ctr = 1;
    public function definition()
    {
        return [
            'nama'=> 'Lowongan '.self::$ctr++,
            'kategori_id'=>$this->faker->randomElement(Kategori::all()->pluck('kategori_id')),
            'perusahaan_id'=>$this->faker->randomElement(Perusahaan::all()->pluck('perusahaan_id')),
            'kuota'=>$this->faker->numberBetween(10,20),
            'keterangan'=>$this->faker->sentence(10,true),
            'status'=>$this->faker->numberBetween(0,1)
        ];
    }
}
