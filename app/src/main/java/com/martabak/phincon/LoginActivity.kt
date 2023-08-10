package com.martabak.phincon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.martabak.phincon.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("zaky", "login button clicked")


            binding.loginButton.setOnClickListener {
                var email = binding.InputEmail.text.toString()
                var password = binding.inputPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {

                        //transfer this to another activity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)

                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("zaky", "Error (NPR? at firebase auth)")
                        showError(it.exception!!.message!!)
                    }
                }
        }




    }

    fun showError(message : String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }
}