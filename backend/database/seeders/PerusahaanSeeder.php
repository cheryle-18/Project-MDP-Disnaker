<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PerusahaanSeeder extends Seeder
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
        DB::table("perusahaan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("perusahaan")->insert([
            [
                'perusahaan_id' => 1,
                'user_id'=>2,
                'alamat' => 'Jalan Kertajaya VII/07-10'
            ],
        ]);
    }
}
