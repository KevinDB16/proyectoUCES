package com.example.proyectouces

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    private lateinit var dbHelper: UserDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        dbHelper = UserDBHelper(this)
        mostrarSocios()

        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editDni = findViewById<EditText>(R.id.editDNI)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val cardShowForm = findViewById<TextView>(R.id.cardShowForm)
        val formLayout = findViewById<LinearLayout>(R.id.formLayout)

        cardShowForm.setOnClickListener {
            formLayout.visibility = View.VISIBLE
            cardShowForm.visibility = View.GONE
        }

        btnGuardar.setOnClickListener {
            val nombre = editNombre.text.toString().trim()
            val dni = editDni.text.toString().trim()
            val guardado = dbHelper.insertarSocio(nombre, dni)

            if(guardado){
                Toast.makeText(this, "Socio agregado", Toast.LENGTH_SHORT).show()
                editNombre.text.clear()
                editDni.text.clear()
                mostrarSocios()
            } else{
                Toast.makeText(this, "Error al agregar socio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarSocios() {
        val lista = dbHelper.obtenerSocios() // List<String> con nombre y DNI
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        val listView = findViewById<ListView>(R.id.listSocios)
        listView.adapter = adapter

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val item = lista[position]
            val dni = item.substringAfterLast(" - ").trim()

            AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Querés eliminar a $item?")
                .setPositiveButton("Sí") { _, _ ->
                    dbHelper.eliminarSocioPorDni(dni)
                    mostrarSocios() // Recarga la lista
                    Toast.makeText(this, "Socio eliminado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }
    }


}