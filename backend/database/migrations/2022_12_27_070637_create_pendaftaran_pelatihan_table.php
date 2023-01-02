<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        DB::statement("SET FOREIGN_KEY_CHECKS=0");
        Schema::create('pendaftaran_pelatihan', function (Blueprint $table) {
            $table->id('pp_id');
            $table->unsignedBigInteger('pelatihan_id');
            $table->unsignedBigInteger('peserta_id');
            $table->foreign('pelatihan_id')->references
            ('pelatihan_id')->on('pelatihan')->onDelete('cascade');
            $table->foreign('peserta_id')->references
            ('peserta_id')->on('peserta')->onDelete('cascade');
            $table->dateTime('tgl_pendaftaran');
            $table->dateTime('tgl_wawancara');
            $table->tinyInteger('status_pendaftaran')->comment('0 = pending, 1 = wawancara, 2 = sedang pelatihan, 3 = selesai, 4 = ditolak');
            $table->tinyInteger('status_kelulusan')->nullable()->comment('0 = pending, 1 = diterima, 2 = ditolak');
            $table->timestamps();
            $table->softDeletes();
        });
        DB::statement("SET FOREIGN_KEY_CHECKS=1");
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pendaftaran_pelatihan');
    }
};
