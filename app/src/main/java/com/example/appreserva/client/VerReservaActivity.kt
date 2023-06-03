package com.example.appreserva.client

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.R
import com.example.appreserva.database.ReservaDatabaseHelper

class VerReservaActivity : AppCompatActivity() {
    private lateinit var textViewReservas: TextView
    private lateinit var buttonBack: Button
    private lateinit var databaseHelper: ReservaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showresevas)

        textViewReservas = findViewById(R.id.textViewReservas)


        databaseHelper = ReservaDatabaseHelper(this)

        displayReservas()
        setupBackButton()
    }

    private fun displayReservas() {
        val reservas = databaseHelper.getAllReservas()
        val stringBuilder = StringBuilder()

        for (reserva in reservas) {
            val quantity = reserva.quantity
            val date = reserva.date
            val zone = reserva.zone

            stringBuilder.append("Cantidad: $quantity\n")
            stringBuilder.append("Fecha: $date\n")
            stringBuilder.append("Zona: $zone\n\n")
        }

        textViewReservas.text = stringBuilder.toString()
    }

    private fun setupBackButton() {
        buttonBack.setOnClickListener {
            finish()
        }
    }
}
