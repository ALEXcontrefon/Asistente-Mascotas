package com.alexcontre.asistentemascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button // Nuevo botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar los elementos de la vista
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton) // Inicializar el botón de registro

        // Setear el listener para el botón de login
        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isEmpty()) {
                showAlert("Error", "Por favor, ingresa una dirección de correo electrónico.")
            } else if (!isValidEmail(email)) {
                showAlert("Error", "La dirección de correo electrónico no es válida.")
            } else if (password.isEmpty()) {
                showAlert("Error", "Por favor, ingresa una contraseña.")
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Setear el listener para el botón de registro
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // Redirigir a la actividad de registro
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && (email.contains(".com") || email.contains(".org") || email.contains(".net"))
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}
