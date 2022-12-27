<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;

class AuthController extends Controller
{
    //
    function doLogin(Request $request)
    {

    }
    function doRegister(Request $request)
    {
        //create a new user
        $newUser = User::create([
            "nama"=> $request->nama,
            "username"=>$request->username,
            "password"=>$request->password,
            "role"=>$request->role
        ]);

        return response()->json($newUser, 201);
    }
}
