<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
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
        Schema::create('pelatihan', function (Blueprint $table) {
            $table->id('pelatihan_id');
            $table->string('nama',100);
            $table->unsignedBigInteger('kategori_id');
            $table->foreign('kategori_id')->references
            ('kategori_id')->on('kategori')->onDelete('cascade');
            $table->integer('kuota')->default(0);
            $table->integer('durasi')->default(0);
            $table->integer('pendidikan_id');
            $table->string('keterangan',255);
            $table->tinyInteger('status')->comment('0 = tidak aktif, 1 = aktif');
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pelatihan');
    }
};
