package com.example.appreserva.client

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.R

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Asignar el click listener a los botones
        findViewById<View>(R.id.btnReserva).setOnClickListener(this)
        findViewById<View>(R.id.btnVerReserva).setOnClickListener(this)
        findViewById<View>(R.id.btnMenu).setOnClickListener(this)
        findViewById<View>(R.id.btnUsuario).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnReserva -> {
                val intent = Intent(this, ReservaActivity::class.java)
                startActivity(intent)
            }
            R.id.btnVerReserva -> {
                val intent = Intent(this, VerReservaActivity::class.java)
                startActivity(intent)
            }
            R.id.btnMenu -> {
                val intent = Intent(this, MenuPrincipalActivity::class.java)
                startActivity(intent)
            }
            R.id.btnUsuario -> {
                val intent = Intent(this, UsuarioActivity::class.java)
                startActivity(intent)
            }
        }
    }
}


