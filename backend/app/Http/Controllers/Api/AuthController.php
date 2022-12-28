<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class AuthController extends Controller
{


    function doLogin(Request $request)
    {
        //login
        $credentials = request(['username', 'password']);

        $user = null;
        $status = 0;
        $message = "Gagal login!";

        if(Auth::attempt($credentials)){
            $user = [Auth::user()];
            $status = 1;
            $message = "Berhasil login!";
        }

        return response()->json([
            "userResponse"=> $user,
            "status" => $status,
            "message"=>$message,

        ],201);
    }
    public function validateDataRegister($data){
        //Cek semua data
        $validate = [];
        $validate["username"] = 'max:50|unique:user';
        $validate["password"] = 'min:8|regex:/^\S*$/u';
        $validator = Validator::make($data,$validate,[
            'username.unique'=> "Username sudah terdaftar!",
            'username.max' => "Panjang username maksimal 50 huruf!",
            'password.min' => "Panjang password minimal 8 huruf!",
        ]);

        return response()->json([
            'success' => !$validator->fails(),
            'messages'=>$validator->errors(),
        ]);
    }

    function doRegister(Request $request)
    {
        //create a new user
        $validate = json_decode($this->validateDataRegister($request->all())->content(),false);
        $newUser = null;
        $status = 0;
        $message = "";

        if($validate->success){
            $newUser = User::create([
                "nama"=> $request->nama,
                "username"=>$request->username,
                "password"=>Hash::make($request->password),
                "role"=>$request->role
            ]);
            $newUser = [$newUser];
            $status = 1;
            $message = "Berhasil register!";
        }
        else{
            $messages = get_object_vars($validate->messages);
            $message = array_values($messages)[0][0];

        }

        return response()->json([
            "userResponse" => $newUser,
            "status" => $status,
            "message" => $message
        ], 201);
    }
}
