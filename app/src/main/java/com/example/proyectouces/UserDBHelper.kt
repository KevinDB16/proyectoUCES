package com.example.proyectouces

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "UsuariosDB", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                contrasenia TEXT
            ) """.trimIndent())

        db.execSQL("INSERT INTO usuarios (nombre, contrasenia) VALUES ('admin', '1234')")
        db.execSQL("INSERT INTO usuarios (nombre, contrasenia) VALUES ('admin2', '123456')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun login(nombre: String, contrasenia: String) : Boolean{
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?",
            arrayOf(nombre, contrasenia)
        )
        val existe = cursor.count > 0
        return existe
    }
}

