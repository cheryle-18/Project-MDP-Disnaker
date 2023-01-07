<?php

namespace Database\Seeders;

use Carbon\Carbon;
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
                'nama'=>'Industri',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 2,
                'nama'=>'Jasa',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 3,
                'nama'=>'Otomotif',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 4,
                'nama'=>'Pariwisata',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 5,
                'nama'=>'Perkantoran',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 6,
                'nama'=>'Teknologi Informasi',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'kategori_id' => 7,
                'nama'=>'Kesenian',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
        ]);
    }
}
