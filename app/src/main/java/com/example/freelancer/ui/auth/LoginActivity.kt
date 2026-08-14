package com.example.freelancer.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.freelancer.R
import com.example.freelancer.data.supabase
import com.example.freelancer.ui.HomeActivity
import com.example.freelancer.ui.auth.SignupActivity
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var footerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supabase.handleDeeplinks(intent)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        footerLink = findViewById(R.id.footerLink)

        btnLogin.setOnClickListener {
            login()
        }

        footerLink.setOnClickListener {
            startActivity(
                Intent(this, SignupActivity::class.java)
            )
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        supabase.handleDeeplinks(intent)
    }

    private fun login() {

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            etEmail.error = "Enter email"
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Enter password"
            return
        }

        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {

                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }

                Toast.makeText(
                    this@LoginActivity,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this@LoginActivity,
                        HomeActivity::class.java
                    )
                )

                finish()

            } catch (e: Exception) {

                Toast.makeText(
                    this@LoginActivity,
                    e.message ?: "Login failed",
                    Toast.LENGTH_LONG
                ).show()

                btnLogin.isEnabled = true
            }
        }
    }
}