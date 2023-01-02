<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Perusahaan;
use App\Models\Peserta;
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
            $user = Auth::user();

            $userRet = [];
            if($user->role==0){
                $peserta = $user->peserta;
                $userRet = [
                    "user_id" => $user->user_id,
                    "nama" => $user->nama,
                    "email" => $user->email,
                    "username" => $user->username,
                    "password" => $user->password,
                    "telp" => $user->telp,
                    "role" => $user->role,
                    "peserta_id" => $peserta->peserta_id,
                    "nik" => $peserta->nik,
                    "pendidikan" => $peserta->pendidikan->nama,
                    "jurusan" => $peserta->jurusan,
                    "nilai" => $peserta->nilai,
                    "status" => $peserta->status,
                ];
            }
            else if($user->role==1){
                $perusahaan = $user->perusahaan;
                $userRet = [
                    "user_id" => $user->user_id,
                    "nama" => $user->nama,
                    "email" => $user->email,
                    "username" => $user->username,
                    "password" => $user->password,
                    "telp" => $user->telp,
                    "role" => $user->role,
                    "perusahaan_id" => $perusahaan->perusahaan_id,
                    "alamat" => $perusahaan->alamat
                ];
            }

            $status = 1;
            $message = "Berhasil login!";
        }

        return response()->json([
            "userResponse"=> [$userRet],
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

            if($request->role == 0){
                //peserta
                Peserta::create([
                    "user_id"=>$newUser->user_id,
                    "status"=>0
                ]);
            }
            else if($request->role == 1){
                //perusahaan
                Perusahaan::create([
                    "user_id"=>$newUser->user_id,
                ]);
            }

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
