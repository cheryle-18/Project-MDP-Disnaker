<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PesertaSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("peserta")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("peserta")->insert([
            [
                'peserta_id' => 1,
                'user_id'=>1,
                'nik'=>'1234567890',
                'tgl_lahir'=>'2000-01-01',
                'pendidikan_id'=>4,
                'jurusan'=>'IPA',
                'nilai'=>75,
                'status'=>2
            ],
            [
                'peserta_id' => 2,
                'user_id'=>3,
                'nik'=>'1234567890',
                'tgl_lahir'=>'2001-01-01',
                'pendidikan_id'=>4,
                'jurusan'=>'IPS',
                'nilai'=>80,
                'status'=>0
            ],
        ]);
    }
}
