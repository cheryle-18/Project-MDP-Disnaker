<?php

namespace Database\Seeders;

use App\Models\SyaratLowongan;
use Carbon\Carbon;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class SyaratLowonganSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("syarat_lowongan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("syarat_lowongan")->insert([
            [
                'sl_id' => 1,
                'lowongan_id' => 1,
                'deskripsi' => 'Lorem ipsum dolor sit amet.',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'sl_id' => 2,
                'lowongan_id' => 1,
                'deskripsi' => 'Lorem ipsum dolor sit amet consectetur.',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ]
        ]);

        SyaratLowongan::factory()->count(80)->create();
    }
}
