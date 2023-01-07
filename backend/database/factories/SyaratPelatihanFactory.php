<?php

namespace Database\Factories;

use App\Models\Pelatihan;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\SyaratPelatihan>
 */
class SyaratPelatihanFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
           'pelatihan_id'=>$this->faker->randomElement(Pelatihan::all()->pluck('pelatihan_id')),
           'deskripsi'=>$this->faker->sentence(4,true)
        ];
    }
}
