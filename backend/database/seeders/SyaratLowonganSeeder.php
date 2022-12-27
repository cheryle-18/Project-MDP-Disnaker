<?php

namespace Database\Seeders;

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
                'deskripsi' => 'tes syarat 1'
            ],
            [
                'sl_id' => 2,
                'lowongan_id' => 1,
                'deskripsi' => 'tes syarat 2'
            ]
        ]);
    }
}
