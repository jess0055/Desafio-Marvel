package com.example.marvelhq.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.marvelhq.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val textPassword by lazy { findViewById<TextInputLayout>(R.id.text_layout_password_login) }
    private val editPassword by lazy { findViewById<TextInputEditText>(R.id.edit_password_login) }
    private val textEmail by lazy { findViewById<TextInputLayout>(R.id.text_layout_email_login) }
    private val editEmail by lazy { findViewById<TextInputEditText>(R.id.edit_email_login) }
    private val btnLogin by lazy { findViewById<Button>(R.id.btn_login) }
    private val btnRegister by lazy { findViewById<Button>(R.id.btn_create_account) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if (validateLogin()){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLogin() : Boolean{

        val email = editEmail?.text.toString()
        val password = editPassword?.text.toString()
        var login = false

        when {

            email.isBlank() -> {
                textEmail?.error = "Email is required"
                textPassword?.error = null
            }

            password.isBlank() -> {
                textEmail?.error = null
                textPassword?.error = "Password is required"
            }
            else -> {
                textEmail?.error = null
                textPassword?.error = null
                login = true
            }
        }
        return login
    }
}