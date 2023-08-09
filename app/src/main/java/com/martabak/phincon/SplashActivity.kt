package com.martabak.phincon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Log.d("zaky", "splash launched")
        var intent : Intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
        // only go to main activity after 1,5 sec
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                startActivity(intent)
                finish()
            }, 3000
        )

    }
}