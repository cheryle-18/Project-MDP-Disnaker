package com.example.projectdisnaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.local.AppDatabase
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.peserta.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {
    val ioScope = CoroutineScope(Dispatchers.IO)
    lateinit var db: AppDatabase

    val addLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
              result: ActivityResult ->
        if(result.resultCode == RESULT_OK) {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        val actionBar = supportActionBar
        actionBar?.hide()

        db = AppDatabase.build(this)

        autoLogin()
    }

    private fun autoLogin(){
        ioScope.launch {
            var userToken = ""
            var temp = db.usersDao.getUserToken()

            runOnUiThread {
                if(temp!=null){
                    userToken = temp.api_key
                }

                if(userToken!=""){ //saved login data
                    var client = ApiConfiguration.getApiService().autoLogin(userToken)
                    client.enqueue(object: Callback<UserResponse> {
                        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                            if(response.isSuccessful){
                                val responseBody = response.body()
                                if(responseBody!=null){
                                    if(responseBody.status == 1){
                                        //successful attempt
                                        val loginedUser = responseBody.userResponse!![0]!!
                                        //check login based on role
                                        if(loginedUser.role == 0){
                                            Handler().postDelayed(Runnable {
                                                val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                                                i.putExtra("user", loginedUser)
                                                addLauncher.launch(i)
                                            }, 2000)
                                        }
                                        else if(loginedUser.role == 1){
                                            Handler().postDelayed(Runnable {
                                                val i = Intent(this@SplashScreenActivity, PerusahaanActivity::class.java)
                                                i.putExtra("user", loginedUser)
                                                addLauncher.launch(i)
                                            }, 2000)
                                        }
                                    }

                                }
                                else{
                                    println("${response.message()}")
                                }
                            }
                            else{
                                Log.d("Error Main Activity",response.toString())
                            }
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            Log.d("Error Main Activity", "${t.message}")
                        }
                    })
                }
                else{
                    Handler().postDelayed(Runnable {
                        val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }, 2000)
                }
            }
        }
    }
}