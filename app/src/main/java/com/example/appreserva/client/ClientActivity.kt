package com.example.appreserva.client

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appreserva.R
import com.example.appreserva.activities.ReservaActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class ClientActivity: AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item -> // Obtener el ID del ítem seleccionado
            val itemId = item.itemId
            when (itemId) {
                R.id.profile -> {
                    // Realizar la navegación a la vista 1
                    val intent1 = Intent(this, ReservaActivity::class.java)
                    startActivity(intent1)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


    }

}