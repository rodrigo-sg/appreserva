package com.example.appreserva.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.R
import android.os.Bundle
import android.widget.EditText
import java.util.*

class ReservaActivity: AppCompatActivity() {

    private lateinit var editTextBirthdate: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        editTextBirthdate = findViewById(R.id.editTextBirthdate)

        //Mostrar Date Picker
        editTextBirthdate.setOnClickListener {
            showDatePicker()
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