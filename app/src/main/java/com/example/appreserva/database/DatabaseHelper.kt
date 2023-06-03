package com.example.appreserva.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appreserva.model.Cliente

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

    fun insertCliente(cliente: Cliente): Long {
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, cliente.nombre)
        values.put(COLUMN_EMAIL, cliente.email)
        values.put(COLUMN_FECHA_NACIMIENTO, cliente.fechaNacimiento)
        values.put(COLUMN_DNI, cliente.dni)
        values.put(COLUMN_PASSWORD, cliente.password)


        val db = writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        return id
    }

    //Validacion de DNI y EMAIL
    fun checkExistingEmail(email: String): Boolean {
        val db = readableDatabase
        val query = "SELECT $COLUMN_EMAIL FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun checkExistingDni(dni: String): Boolean {
        val db = readableDatabase
        val query = "SELECT $COLUMN_DNI FROM $TABLE_NAME WHERE $COLUMN_DNI = ?"
        val cursor = db.rawQuery(query, arrayOf(dni))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getEmailAndPassword(): Pair<String, String>? {
        val db = readableDatabase
        val columns = arrayOf(COLUMN_EMAIL, COLUMN_PASSWORD)
        val cursor: Cursor? = db.query(
            TABLE_NAME, columns, null, null, null, null, null
        )
        var email: String? = null
        var password: String? = null

        cursor?.use {
            if (cursor.moveToFirst()) {
                val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
                val passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)
                if (emailIndex != -1 && passwordIndex != -1) {
                    email = cursor.getString(emailIndex)
                    password = cursor.getString(passwordIndex)
                }
            }
        }

        cursor?.close()
        db.close()

        return if (email != null && password != null) Pair(email!!, password!!)
        else null
    }

    // Aquí puedes agregar métodos para actualizar, eliminar y consultar registros de clientes según tus necesidades.
}