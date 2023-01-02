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
        Schema::create('peserta', function (Blueprint $table) {
            $table->id('peserta_id');
            $table->unsignedBigInteger('user_id');
            $table->foreign('user_id')->references
            ('user_id')->on('user')->onDelete('cascade');
            $table->string('nik',20)->nullable();
            $table->date('tgl_lahir')->nullable();
            $table->integer('pendidikan_id')->nullable();
            $table->string('jurusan',50)->nullable();
            $table->integer('nilai')->nullable();
            $table->tinyInteger('status')->comment('0 = free, 1 = sedang pelatihan, 2 = kerja');
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
        Schema::dropIfExists('peserta');
    }
};
