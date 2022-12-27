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
        Schema::create('pendaftaran_lowongan', function (Blueprint $table) {
            $table->id('pl_id');
            $table->unsignedBigInteger('lowongan_id');
            $table->unsignedBigInteger('peserta_id');
            $table->foreign('lowongan_id')->references
            ('lowongan_id')->on('lowongan')->onDelete('cascade');
            $table->foreign('peserta_id')->references
            ('peserta_id')->on('peserta')->onDelete('cascade');
            $table->dateTime('tanggal');
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
        Schema::dropIfExists('pendaftaran_lowongan');
    }
};
