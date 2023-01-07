<?php

namespace Database\Seeders;

use App\Models\User;
use Carbon\Carbon;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Hidehalo\Nanoid\Client;
use Hidehalo\Nanoid\GeneratorInterface;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        $client = new Client();

        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("user")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("user")->insert([
            [
                'user_id' => 1,
                'nama'=>'John Doe',
                'email' => 'johndoe@gmail.com',
                'username'=>'johndoe',
                'password'=>Hash::make(123),
                'telp'=>'0123456789',
                'role'=>0,
                'api_key' => $client->generateId($size = 16, $mode = Client::MODE_DYNAMIC),
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'user_id' => 2,
                'nama'=>'PT XYZ',
                'email' => 'xyz@gmail.com',
                'username'=>'xyz',
                'password'=>Hash::make(123),
                'telp'=>'1234567890',
                'role'=>1,
                'api_key' => $client->generateId($size = 16, $mode = Client::MODE_DYNAMIC),
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'user_id' => 3,
                'nama'=>'Jane Doe',
                'email' => 'janedoe@gmail.com',
                'username'=>'janedoe',
                'password'=>Hash::make(123),
                'telp'=>'0123456789',
                'role'=>0,
                'api_key' => $client->generateId($size = 16, $mode = Client::MODE_DYNAMIC),
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ]
        ]);

        User::factory()->count(20)->create();
    }

}
