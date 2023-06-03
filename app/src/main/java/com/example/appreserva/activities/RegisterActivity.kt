package com.example.appreserva.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.R
import android.app.DatePickerDialog
import android.widget.*
import com.example.appreserva.database.DatabaseHelper
import com.example.appreserva.model.Cliente
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var editTextBirthdate: EditText
    private lateinit var dniEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var checkboxTermsConditions: CheckBox
    private lateinit var registerButton: Button
    private lateinit var backButton: TextView
    private lateinit var changePasswordButton: TextView


    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.editTextName)
        editTextBirthdate = findViewById(R.id.editTextBirthdate)
        dniEditText = findViewById(R.id.editTextDNI)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        checkboxTermsConditions = findViewById(R.id.checkboxTermsConditions)
        registerButton = findViewById(R.id.buttonRegister)
        backButton = findViewById(R.id.buttonBackToLogin)
        changePasswordButton = findViewById(R.id.buttonChangePassword)


        databaseHelper = DatabaseHelper(this)

        registerButton.setOnClickListener {
            val nombre = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val fechaNacimiento = editTextBirthdate.text.toString().trim()
            val dni = dniEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || fechaNacimiento.isEmpty() || dni.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (checkboxTermsConditions.isChecked) {

                registerCliente(nombre, email, fechaNacimiento, dni, password)
            } else {
                Toast.makeText(this, "Debe aceptar los términos y condiciones para registrarse", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



        }

        //Mostrar Date Picker
        editTextBirthdate.setOnClickListener {
            showDatePicker()
        }

        //Boton para regresar al log in
        backButton.setOnClickListener {
            finish()
        }

        //Boton para ir al apartado de cambiar contraseña
        changePasswordButton.setOnClickListener {
            // Implementa el código para cambiar la contraseña aquí
        }
    }

    private fun registerCliente(nombre: String, email: String, fechaNacimiento: String, dni: String, password: String) {
        // Verificar si el correo ya está registrado
        val existingEmail = databaseHelper.checkExistingEmail(email)
        if (existingEmail) {
            Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si el DNI ya está registrado
        val existingDni = databaseHelper.checkExistingDni(dni)
        if (existingDni) {
            Toast.makeText(this, "El DNI ya está registrado", Toast.LENGTH_SHORT).show()
            return
        }

        // Insertar el nuevo registro
        val cliente = Cliente(nombre, email, fechaNacimiento, dni, password)
        val id = databaseHelper.insertCliente(cliente)

        if (id != -1L) {
            // Registro exitoso
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

            // Redirigir a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Error al registrar
            Toast.makeText(this, "Error al registrar el cliente", Toast.LENGTH_SHORT).show()
        }
    }





    //Codigo para el DatePicker
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = formatDate(selectedYear, selectedMonth, selectedDay)
                editTextBirthdate.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val dateFormat = android.text.format.DateFormat.getDateFormat(applicationContext)
        return dateFormat.format(calendar.time)
    }
}

