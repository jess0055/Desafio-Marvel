package com.example.marvelhq.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.example.marvelhq.R
import com.example.marvelhq.viewModel.LoginViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val textPassword by lazy { findViewById<TextInputLayout>(R.id.text_layout_password_login) }
    private val editPassword by lazy { findViewById<TextInputEditText>(R.id.edit_password_login) }
    private val textEmail by lazy { findViewById<TextInputLayout>(R.id.text_layout_email_login) }
    private val editEmail by lazy { findViewById<TextInputEditText>(R.id.edit_email_login) }
    private val btnLogin by lazy { findViewById<Button>(R.id.btn_login) }
    private val btnRegister by lazy { findViewById<Button>(R.id.btn_create_account) }

    private val viewModelLogin by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureValidationOfFields()
        configClicks()

//        btnRegister.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun configClicks() {
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun configureValidationOfFields() {
        viewModelLogin.fieldEmail.observe(this) { emailValid ->
            if (emailValid) {
                textEmail.error = null
            } else {
                textEmail.error = "Email is required"
            }

            navigateIfValid()
        }

        viewModelLogin.fieldPassword.observe(this) { passValid ->
            if (passValid) {
                textPassword.error = null
            } else {
                textPassword.error = "Passoword is required"
            }

            navigateIfValid()
        }
    }

    private fun navigateIfValid() {
        if (viewModelLogin.fieldEmail.value == true
            && viewModelLogin.fieldPassword.value == true
        ) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        viewModelLogin.validateEntryFiels(email, password)
    }
}