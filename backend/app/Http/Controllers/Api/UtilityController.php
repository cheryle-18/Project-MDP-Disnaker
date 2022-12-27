<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;

class UtilityController extends Controller
{
    //
    function getUsers()
    {
        return response()->json(User::all(), 200);
    }
}
