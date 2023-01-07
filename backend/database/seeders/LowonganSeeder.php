<?php

namespace Database\Seeders;

use App\Models\Lowongan;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class LowonganSeeder extends Seeder
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
        DB::table("lowongan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("lowongan")->insert([
            [
                'lowongan_id' => 1,
                'nama'=>'Pegawai Administrasi',
                'kategori_id' => 5,
                'perusahaan_id'=>1,
                'kuota'=>10,
                'keterangan'=>'Lorem ipsum dolor sit, amet consectetur adipisicing elit. At, nesciunt.',
                'status'=>1
            ],
            [
                'lowongan_id' => 2,
                'nama'=>'Staff IT',
                'kategori_id' => 6,
                'perusahaan_id'=>1,
                'kuota'=>15,
                'keterangan'=>'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quidem, quo?',
                'status'=>0
            ]
        ]);

        Lowongan::factory()->count(50)->create();
    }
}
