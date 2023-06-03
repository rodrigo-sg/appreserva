package com.example.appreserva.client

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.MainActivity
import com.example.appreserva.R
import com.example.appreserva.activities.LoginActivity
import com.example.appreserva.database.ReservaDatabaseHelper
import java.text.SimpleDateFormat
import java.util.*

class ReservaActivity : AppCompatActivity() {
    private lateinit var editTextQuantity: EditText
    private lateinit var editTextDate: EditText
    private lateinit var radioGroupZone: RadioGroup
    private lateinit var btnRegister: Button
    private lateinit var btnVolver: Button
    private lateinit var databaseHelper: ReservaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        editTextQuantity = findViewById(R.id.editTextQuantity)
        editTextDate = findViewById(R.id.editTextDate)
        radioGroupZone = findViewById(R.id.radioGroupZone)
        btnRegister = findViewById(R.id.buttonRegister)
        btnVolver = findViewById(R.id.buttonBack)

        databaseHelper = ReservaDatabaseHelper(this)

        setupDatePicker()
        setupRegisterButton()
    }

    private fun setupDatePicker() {
        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    editTextDate.setText(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()

            btnVolver.setOnClickListener {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun setupRegisterButton() {
        val buttonRegister: Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            val quantity = editTextQuantity.text.toString().toInt()
            val date = editTextDate.text.toString()
            val selectedRadioButton = findViewById<RadioButton>(radioGroupZone.checkedRadioButtonId)
            val zone = selectedRadioButton.text.toString()

            insertReserva(quantity, date, zone)
        }
    }

    private fun insertReserva(quantity: Int, date: String, zone: String) {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(ReservaDatabaseHelper.COLUMN_QUANTITY, quantity)
            put(ReservaDatabaseHelper.COLUMN_DATE, date)
            put(ReservaDatabaseHelper.COLUMN_ZONE, zone)
        }

        db.insert(ReservaDatabaseHelper.TABLE_NAME, null, contentValues)
        db.close()

        Toast.makeText(this, "Reserva registrada exitosamente", Toast.LENGTH_SHORT).show()
    }
}
