<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class AuthController extends Controller
{
    //
    function doLogin(Request $request)
    {
        //login
        
    }
    public function validateDataRegister($data){
        //Cek semua data
        $validate = [];
        $validate["nama"] = 'required|string|max:255';
        $validate["username"] = 'required|string|max:255|unique:user';
        $validate["password"] = 'required|string|max:255|min:8|regex:/^\S*$/u';
        $validator = Validator::make($data,$validate,[
            'nama.required'=> "Nama harus diisi!",
            'username.required'=> "Username harus diisi!",
            'username.unique'=> "Username sudah terdaftar!",
            'password.required'=> "Password harus diisi!",
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
        if($validate->success){
            $newUser = User::create([
                "nama"=> $request->nama,
                "username"=>$request->username,
                "password"=>Hash::make($request->password),
                "role"=>$request->role
            ]);
            return response()->json([
                "userResponse" => [$newUser],
                "status" => 1,
                "message" => "Berhasil register!"
            ], 201);
        }
        else{
            $messages = get_object_vars($validate->messages);
            $message = array_values($messages)[0][0];
            return response()->json([
                "userResponse"=> null,
                "status" => 0,
                "message"=>$message,

            ],201);
        }
    }
}
