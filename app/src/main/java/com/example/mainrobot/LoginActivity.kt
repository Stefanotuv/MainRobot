package com.example.mainrobot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.actionButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform your login validation here
            val isValidLogin = validateLogin(username, password)

            if (isValidLogin) {
                // Set the login status
                val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_logged_in", true)
                editor.apply()

                // Start the MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        // Implement your login validation logic here
        // Return true if the login is successful, false otherwise
        // For example:
        val validUsername = "admin"
        val validPassword = "123456"

        return username == validUsername && password == validPassword
    }
}
