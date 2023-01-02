<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PendaftaranLowonganSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pendaftaran_lowongan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("pendaftaran_lowongan")->insert([
            [
                'pl_id' => 1,
                'lowongan_id' => 1,
                'peserta_id' => 1,
                'tanggal' => date_create('2022-12-31 15:37:17'),
            ],
        ]);
    }
}
