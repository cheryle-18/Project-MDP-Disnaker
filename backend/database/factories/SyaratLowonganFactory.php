<?php

namespace Database\Factories;

use App\Models\Lowongan;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\SyaratLowongan>
 */
class SyaratLowonganFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            'lowongan_id'=>$this->faker->randomElement(Lowongan::all()->pluck('lowongan_id')),
            'deskripsi'=>$this->faker->sentence(4,true)
         ];
    }
}
