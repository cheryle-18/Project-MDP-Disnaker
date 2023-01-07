<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\User>
 */
class UserFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        $firstname = $this->faker->unique()->firstName();
        $lastname = $this->faker->unique()->lastName();
        return [
            'nama' => $firstname.' '.$lastname,
            'email' => $firstname.'@'.$this->faker->safeEmailDomain(),
            'username' => $this->faker->username(),
            'password' => Hash::make('123'),
            'telp' => $this->faker->phoneNumber(),
            'role' =>$this->faker->numberBetween(0,1)
        ];
    }

    /**
     * Indicate that the model's email address should be unverified.
     *
     * @return static
     */
    public function unverified()
    {
        return $this->state(fn (array $attributes) => [
            'email_verified_at' => null,
        ]);
    }
}
