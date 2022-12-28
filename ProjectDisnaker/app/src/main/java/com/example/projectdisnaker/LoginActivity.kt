package com.example.projectdisnaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectdisnaker.admin.AdminActivity
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.databinding.ActivityLoginBinding
import com.example.projectdisnaker.perusahaan.PerusahaanActivity
import com.example.projectdisnaker.peserta.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log2

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private fun doLogin(){
        var username = binding.edtUsernameLogin.text.toString()
        var password = binding.edtPassLogin.text.toString()

        if(username!="" && password!=""){
            if(username == "admin" && password == "admin"){
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }
            else{
                var client = ApiConfiguration.getApiService().login(username,password)
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
                                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                        intent.putExtra("user", loginedUser)
                                        startActivity(intent)
                                    }
                                    else if(loginedUser.role == 1){
                                        val intent = Intent(this@LoginActivity, PerusahaanActivity::class.java)
                                        intent.putExtra("user", loginedUser)
                                        startActivity(intent)
                                    }
                                }
                                Toast.makeText(this@LoginActivity,responseBody.message, Toast.LENGTH_SHORT).show()

                            }
                            else{
                                println("${response.message()}")
                                Toast.makeText(this@LoginActivity,response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Log.d("Error Login Activity",response.toString())
                            Toast.makeText(this@LoginActivity,response.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Error Login Activity", "${t.message}")
                        Toast.makeText(this@LoginActivity,t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        else{
            Toast.makeText(this,"Semua field harus diisi!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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