package com.example.projectdisnaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectdisnaker.admin.AdminActivity
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.LoginItem
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.databinding.ActivityLoginBinding
import com.example.projectdisnaker.local.AppDatabase
import com.example.projectdisnaker.local.Users
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.peserta.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log2

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val ioScope = CoroutineScope(Dispatchers.IO)
    lateinit var db: AppDatabase

    private fun doLogin(){
        var username = binding.edtUsernameLogin.text.toString()
        var password = binding.edtPassLogin.text.toString()

        if(username!="" && password!=""){
            if(username == "admin" && password == "admin"){
                binding.edtUsernameLogin.setText("")
                binding.edtPassLogin.setText("")

                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }
            else{
                var login = LoginItem(username, password)
                var client = ApiConfiguration.getApiService().login(login)
                client.enqueue(object: Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                if(responseBody.status == 1){
                                    //successful attempt
                                    val loginedUser = responseBody.userResponse!![0]!!

                                    //save api key to local storage
                                    ioScope.launch {
                                        db.usersDao.saveToken(Users(loginedUser.apiKey!!))
                                        runOnUiThread {
                                            //check login based on role
                                            if(loginedUser.role == 0){
                                                binding.edtUsernameLogin.setText("")
                                                binding.edtPassLogin.setText("")

                                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                                intent.putExtra("user", loginedUser)
                                                startActivity(intent)
                                            }
                                            else if(loginedUser.role == 1){
                                                binding.edtUsernameLogin.setText("")
                                                binding.edtPassLogin.setText("")

                                                val intent = Intent(this@LoginActivity, PerusahaanActivity::class.java)
                                                intent.putExtra("user", loginedUser)
                                                startActivity(intent)
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                println("${response.message()}")
                                Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Log.d("Error Login Activity",response.toString())
                            Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Error Login Activity", "${t.message}")
                        Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        else{
            Toast.makeText(this,"Harap isi semua data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = AppDatabase.build(this)

        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}