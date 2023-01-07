<?php

namespace Database\Seeders;

use App\Models\Pelatihan;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PelatihanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pelatihan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("pelatihan")->insert([
            [
                'pelatihan_id' => 1,
                'nama'=>'Administrasi Perkantoran',
                'kategori_id' => 5,
                'kuota'=>10,
                'durasi'=>15,
                'pendidikan_id'=>4,
                'keterangan'=>'Lorem ipsum dolor sit, amet consectetur adipisicing elit. At, nesciunt.',
                'status'=>1
            ]
        ]);

        Pelatihan::factory()->count(50)->create();

    }
}
