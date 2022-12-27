<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class KategoriSeeder extends Seeder
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
        DB::table("kategori")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("kategori")->insert([
            [
                'kategori_id' => 1,
                'nama'=>'Industri'
            ],
            [
                'kategori_id' => 2,
                'nama'=>'Jasa'
            ],
            [
                'kategori_id' => 3,
                'nama'=>'Otomotif'
            ],
            [
                'kategori_id' => 4,
                'nama'=>'Pariwisata'
            ],
            [
                'kategori_id' => 5,
                'nama'=>'Perkantoran'
            ],
            [
                'kategori_id' => 6,
                'nama'=>'Teknologi Informasi'
            ],
            [
                'kategori_id' => 7,
                'nama'=>'Kesenian'
            ],
        ]);
    }
}
