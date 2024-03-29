<?php

namespace Database\Seeders;

use Carbon\Carbon;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PendidikanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pendidikan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("pendidikan")->insert([
            [
                'pendidikan_id' => 1,
                'nama'=>'Tidak Memiliki Ijazah',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 2,
                'nama'=>'SD',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 3,
                'nama'=>'SMP',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 4,
                'nama'=>'SMA',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 5,
                'nama'=>'SMK',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 6,
                'nama'=>'Diploma I',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 7,
                'nama'=>'Diploma II',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 8,
                'nama'=>'Diploma III',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 9,
                'nama'=>'Diploma IV',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 10,
                'nama'=>'Strata I',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 11,
                'nama'=>'Strata II',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
            [
                'pendidikan_id' => 12,
                'nama'=>'Strata III',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
        ]);
    }
}
