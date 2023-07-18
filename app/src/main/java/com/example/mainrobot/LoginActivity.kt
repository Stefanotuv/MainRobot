package com.example.mainrobot
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mainrobot.MainActivity
import com.example.mainrobot.R

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var switchTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            startMainActivity()
            return
        }

        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        loginButton = findViewById(R.id.actionButton)
        switchTextView = findViewById(R.id.switchTextView)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isRegisterMode()) {
                val confirmPassword = confirmPasswordEditText.text.toString()
                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                // Perform registration logic here
                registerUser(username, password)
            } else {
                // Perform your login validation here
                val isValidLogin = validateLogin(username, password)

                if (isValidLogin) {
                    sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
                    startMainActivity()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        switchTextView.setOnClickListener {
            setRegisterMode(!isRegisterMode())
        }

        setRegisterMode(isRegisterMode())
    }

    private fun validateLogin(username: String, password: String): Boolean {
        val validUsername = "admin"
        val validPassword = "123456"
        return username == validUsername && password == validPassword
    }

    private fun registerUser(username: String, password: String) {
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
    }

    private fun isRegisterMode(): Boolean {
        return sharedPreferences.getBoolean("is_register_mode", false)
    }

    private fun setRegisterMode(registerMode: Boolean) {
        sharedPreferences.edit().putBoolean("is_register_mode", registerMode).apply()
        if (registerMode) {
            confirmPasswordEditText.visibility = View.VISIBLE
            loginButton.text = "Register"
            switchTextView.text = "Already have an account? Login here."
        } else {
            confirmPasswordEditText.visibility = View.GONE
            loginButton.text = "Login"
            switchTextView.text = "Don't have an account? Register here."
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


