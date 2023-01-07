<?php

namespace Database\Seeders;

use App\Models\SyaratPelatihan;
use Carbon\Carbon;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class SyaratPelatihanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("syarat_pelatihan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("syarat_pelatihan")->insert([
            [
                'sp_id' => 1,
                'pelatihan_id' => 1,
                'deskripsi' => 'Lorem ipsum dolor sit amet.',
                'created_at'=>Carbon::now('Asia/Jakarta'),
                'updated_at'=>Carbon::now('Asia/Jakarta')
            ],
        ]);
        SyaratPelatihan::factory()->count(30)->create();
    }
}
