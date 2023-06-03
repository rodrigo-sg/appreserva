package com.example.appreserva.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appreserva.model.Reserva

class ReservaDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "reserva.db"
        private const val DATABASE_VERSION = 1

        // Definir el nombre de la tabla y los nombres de las columnas
        const val TABLE_NAME = "reservas"
        private const val COLUMN_ID = "_id"
        const val COLUMN_QUANTITY = "cantidad"
        const val COLUMN_DATE = "fecha"
        const val COLUMN_ZONE = "zona"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_QUANTITY INTEGER, $COLUMN_DATE TEXT, $COLUMN_ZONE TEXT);"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar la tabla si existe y crear una nueva versión
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertReserva(quantity: Int, date: String, zone: String) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, quantity)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_ZONE, zone)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }


    fun getAllReservas(): List<Reserva> {
        val reservas = mutableListOf<Reserva>()
        val db = readableDatabase

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_QUANTITY,
            COLUMN_DATE,
            COLUMN_ZONE
        )

        val cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val quantity = getInt(getColumnIndexOrThrow(COLUMN_QUANTITY))
                val date = getString(getColumnIndexOrThrow(COLUMN_DATE))
                val zone = getString(getColumnIndexOrThrow(COLUMN_ZONE))

                val reserva = Reserva(id, quantity, date, zone)
                reservas.add(reserva)
            }
        }

        cursor.close()
        return reservas
    }

}
