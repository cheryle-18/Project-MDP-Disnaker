<?php

namespace Database\Factories;

use App\Models\Kategori;
use App\Models\Pendidikan;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Pelatihan>
 */
class PelatihanFactory extends Factory
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
            'nama'=> 'Pelatihan '.self::$ctr++,
            'kategori_id'=>$this->faker->randomElement(Kategori::all()->pluck('kategori_id')),
            'kuota'=>$this->faker->numberBetween(10,20),
            'durasi'=>$this->faker->numberBetween(12,50),
            'pendidikan_id'=>$this->faker->randomElement(Pendidikan::all()->pluck('pendidikan_id')),
            'keterangan'=>$this->faker->sentence(10,true),
            'status'=>$this->faker->numberBetween(0,1)
        ];
    }
}
