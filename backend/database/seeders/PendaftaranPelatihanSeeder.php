<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PendaftaranPelatihanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        DB::table("pendaftaran_pelatihan")->truncate();
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
        DB::table("pendaftaran_pelatihan")->insert([
            [
                'pp_id' => 1,
                'pelatihan_id' => 1,
                'peserta_id' => 1,
                'tgl_pendaftaran' => date_create('2022-12-31 15:37:17'),
                'tgl_wawancara' => date_create('2023-01-15 00:00:00'),
                'status_pendaftaran' => 3,
                'status_kelulusan' => 1
            ],
            [
                'pp_id' => 2,
                'pelatihan_id' => 1,
                'peserta_id' => 1,
                'tgl_pendaftaran' => date_create('2022-12-31 15:37:17'),
                'tgl_wawancara' => date_create('2023-01-15 00:00:00'),
                'status_pendaftaran' => 0,
                'status_kelulusan' => 0
            ],
            [
                'pp_id' => 3,
                'pelatihan_id' => 1,
                'peserta_id' => 1,
                'tgl_pendaftaran' => date_create('2022-12-31 15:37:17'),
                'tgl_wawancara' => date_create('2023-01-15 00:00:00'),
                'status_pendaftaran' => 1,
                'status_kelulusan' => 0
            ],
            [
                'pp_id' => 4,
                'pelatihan_id' => 1,
                'peserta_id' => 1,
                'tgl_pendaftaran' => date_create('2022-12-31 15:37:17'),
                'tgl_wawancara' => date_create('2023-01-15 00:00:00'),
                'status_pendaftaran' => 2,
                'status_kelulusan' => 0
            ],
            [
                'pp_id' => 5,
                'pelatihan_id' => 1,
                'peserta_id' => 1,
                'tgl_pendaftaran' => date_create('2022-12-31 15:37:17'),
                'tgl_wawancara' => date_create('2023-01-15 00:00:00'),
                'status_pendaftaran' => 4,
                'status_kelulusan' => 1
            ],
        ]);
    }
}
