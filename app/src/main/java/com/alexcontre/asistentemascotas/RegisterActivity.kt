package com.alexcontre.asistentemascotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLoginButton: Button // Inicializar botón de regreso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar los elementos de la vista
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        registerButton = findViewById(R.id.registerButton)
        backToLoginButton = findViewById(R.id.backToLoginButton) // Inicializar botón de regreso

        // Configurar el listener del botón de registro
        registerButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isEmpty()) {
                showAlert("Error", "Por favor, ingresa una dirección de correo electrónico.")
            } else if (!isValidEmail(email)) {
                showAlert("Error", "La dirección de correo electrónico no es válida.")
            } else if (password.isEmpty()) {
                showAlert("Error", "Por favor, ingresa una contraseña.")
            } else {
                showAlert("Éxito", "Registro completado correctamente.")
            }
        }

        // Configurar el listener para regresar al LoginActivity
        backToLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }
    }

    // Validar si el correo tiene formato válido
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && (email.contains(".com") || email.contains(".org") || email.contains(".net"))
    }

    // Mostrar mensajes de alerta
    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}
