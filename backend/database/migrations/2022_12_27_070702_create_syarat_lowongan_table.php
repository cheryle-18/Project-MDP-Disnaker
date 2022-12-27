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
        Schema::create('syarat_lowongan', function (Blueprint $table) {
            $table->id('sl_id');
            $table->unsignedBigInteger('lowongan_id');
            $table->foreign('lowongan_id')->references
            ('lowongan_id')->on('lowongan')->onDelete('cascade');
            $table->string('deskripsi',150);
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
        Schema::dropIfExists('syarat_lowongan');
    }
};
