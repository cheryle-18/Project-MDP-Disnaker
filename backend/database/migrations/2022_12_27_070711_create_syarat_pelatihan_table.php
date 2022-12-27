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
        Schema::create('syarat_pelatihan', function (Blueprint $table) {
            $table->id('sp_id');
            $table->unsignedBigInteger('pelatihan_id');
            $table->foreign('pelatihan_id')->references
            ('pelatihan_id')->on('pelatihan')->onDelete('cascade');
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
        Schema::dropIfExists('syarat_pelatihan');
    }
};
