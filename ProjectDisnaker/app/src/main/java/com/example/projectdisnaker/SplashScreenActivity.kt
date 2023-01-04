package com.example.projectdisnaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        val actionBar = supportActionBar
        actionBar?.hide()

        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)
    }
}