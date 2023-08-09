package com.martabak.phincon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

import com.martabak.phincon.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth =  FirebaseAuth.getInstance()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var inputEmail = binding.InputEmail
        var inputName = binding.inputName
        var inputKTP = binding.inputKTP
        var inputPassword = binding.inputPassword
        var inputPassword2 = binding.inputPassword2

        //now make listener HAHA
        binding.registerButton.setOnClickListener {
            if (validation()) {
                auth.createUserWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString())
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            Log.d("zaky", "SIgn up success, check firebase")
                        } else {
                            showError("ERROR")
                        }
                    }
            }
        }


    }

    fun validation() : Boolean {
        if (isFormFilled()) {
            return true
        } else {
            showError("Please fill all forms")
            return false
        }
    }

    fun isFormFilled() : Boolean {
        var filled = false
        var email = binding.InputEmail.text.toString()
        var name = binding.inputName.text.toString()
        var ktp = binding.inputKTP.text.toString()
        var pass = binding.inputPassword.text.toString()
        var pass2 = binding.inputPassword2.text.toString()

        if (email != "" && name != "" && ktp != "" && pass != "" && pass2 != "") {
            filled = true
        }

        return filled
    }

    fun showError(message : String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
    }
}