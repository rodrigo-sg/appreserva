package com.example.appreserva.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.service.autofill.UserData

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "clientes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "clientes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
        private const val COLUMN_DNI = "dni"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_FECHA_NACIMIENTO TEXT, " +
                "$COLUMN_DNI TEXT, " +
                "$COLUMN_PASSWORD TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertCliente(nombre: String, email: String, fechaNacimiento: String, dni: String, password: String): Long {
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_FECHA_NACIMIENTO, fechaNacimiento)
        values.put(COLUMN_DNI, dni)
        values.put(COLUMN_PASSWORD, password)

        val db = writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        return id
    }


    // Aquí puedes agregar métodos para actualizar, eliminar y consultar registros de clientes según tus necesidades.
}
