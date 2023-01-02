<?php

namespace Database\Seeders;

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
                'nama'=>'Tidak Memiliki Ijazah'
            ],
            [
                'pendidikan_id' => 2,
                'nama'=>'SD'
            ],
            [
                'pendidikan_id' => 3,
                'nama'=>'SMP'
            ],
            [
                'pendidikan_id' => 4,
                'nama'=>'SMA'
            ],
            [
                'pendidikan_id' => 5,
                'nama'=>'SMK'
            ],
            [
                'pendidikan_id' => 6,
                'nama'=>'Diploma I'
            ],
            [
                'pendidikan_id' => 7,
                'nama'=>'Diploma II'
            ],
            [
                'pendidikan_id' => 8,
                'nama'=>'Diploma III'
            ],
            [
                'pendidikan_id' => 9,
                'nama'=>'Diploma IV'
            ],
            [
                'pendidikan_id' => 10,
                'nama'=>'Strata I'
            ],
            [
                'pendidikan_id' => 11,
                'nama'=>'Strata II'
            ],
            [
                'pendidikan_id' => 12,
                'nama'=>'Strata III'
            ],
        ]);
    }
}
