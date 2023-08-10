package com.martabak.phincon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
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

        val db = Firebase.firestore


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
                            val user = auth.currentUser
                            val nikData = hashMapOf(
                                "user_id" to user!!.uid,
                                "NIK" to inputKTP.text.toString()
                            )
                            db.collection("NIK").add(nikData)
                            //start new activity
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            showError(task.exception!!.message!!)
                        }
                    }
            }
        }


    }

    fun validation() : Boolean {
        return isFormFilled() && isPasswordMatch() && isPasswordShort()
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
        } else {
            showError("Please fill all the forms")
        }

        return filled
    }

    fun isPasswordMatch() : Boolean {
        //check if password match password two
        var match : Boolean = binding.inputPassword.text.toString() == binding.inputPassword2.text.toString()
        if (match == false) {
            showError("Inconsistent password")
            return false
        } else {
            return true
        }
    }

    fun isPasswordShort() : Boolean {
        var len = binding.inputPassword.text.toString().length
        if (len < 6) {
            showError("Password must be at least 6 characters")
            return false
        } else {
            return true
        }
    }

    fun showError(message : String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
    }
}