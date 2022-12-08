package com.example.projectdisnaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectdisnaker.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {
            var username = binding.etUsernameLogin.text.toString()
            var password = binding.etPassLogin.text.toString()

            if(username=="admin" && password=="admin"){
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}