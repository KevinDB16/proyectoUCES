package com.example.proyectouces

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "ClubDB", null, 2){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                contrasenia TEXT
            ) """.trimIndent())

        db.execSQL("INSERT INTO usuarios (nombre, contrasenia) VALUES ('admin', '1234')")
        db.execSQL("INSERT INTO usuarios (nombre, contrasenia) VALUES ('admin2', '123456')")

        db.execSQL("""
            CREATE TABLE socios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                dni TEXT UNIQUE
            ) """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuariosDB")
        onCreate(db)
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

    fun insertarSocio(nombre: String, dni: String) : Boolean{
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("dni", dni)
        }

        val resultado = db.insert("socios", null, valores);
        return resultado != -1L
    }

    fun obtenerSocios(): List<String> {
        val socios = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, dni FROM socios", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0)
                val dni = cursor.getString(1)
                socios.add("$nombre - $dni")
            } while (cursor.moveToNext())
        }

        cursor.close()
        return socios
    }


    fun eliminarSocioPorDni(dni: String) {
        val db = writableDatabase
        db.delete("socios", "dni = ?", arrayOf(dni))
    }
}

