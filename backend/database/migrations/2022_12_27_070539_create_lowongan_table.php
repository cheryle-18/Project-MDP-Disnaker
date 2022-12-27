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
        Schema::create('lowongan', function (Blueprint $table) {
            $table->id('lowongan_id');
            $table->string('nama',100);
            $table->unsignedBigInteger('kategori_id');
            $table->unsignedBigInteger('perusahaan_id');
            $table->foreign('kategori_id')->references
            ('kategori_id')->on('kategori')->onDelete('cascade');
            $table->foreign('perusahaan_id')->references
            ('perusahaan_id')->on('perusahaan')->onDelete('cascade');
            $table->integer('kuota')->default(0);
            $table->string('keterangan',150)->nullable();
            $table->tinyInteger('status')->comment('0 = tidak aktif, 1 = aktif');
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
        Schema::dropIfExists('lowongan');
    }
};
