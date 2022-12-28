package com.example.projectdisnaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectdisnaker.api.ApiConfiguration
import com.example.projectdisnaker.api.UserResponse
import com.example.projectdisnaker.databinding.ActivityRegisterBinding
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private fun doRegister(){

        var nama = binding.edtNamaRegister.text.toString()
        var username = binding.edtUsernameRegister.text.toString()
        var password = binding.edtPasswordRegister.text.toString()
        var confirmPass = binding.edtConfirmRegister.text.toString()

        if(nama!="" && username!="" && password!="" && confirmPass!=""){
            if(password == confirmPass){
                var client = ApiConfiguration.getApiService().register(nama,username,password,0) //register as participant
                client.enqueue(object: Callback<UserResponse> {

                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody!=null){
                                Toast.makeText(this@RegisterActivity,responseBody.message,Toast.LENGTH_SHORT).show()
                            }
                            else{
                                println("${response.message()}")
                                Toast.makeText(this@RegisterActivity,response.message(),Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Log.d("Error Register Activity",response.toString())
                            Toast.makeText(this@RegisterActivity,response.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Error Register Activity", "${t.message}")
                        Toast.makeText(this@RegisterActivity,t.message,Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else{
                Toast.makeText(this,"Password harus sama dengan konfirmasi password!",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"Semua field harus diisi!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }
}