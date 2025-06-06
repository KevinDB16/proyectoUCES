package com.example.proyectouces

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UserDBHelper(this)

        val editUserName = findViewById<EditText>(R.id.editUsername)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener{
            val strEditUserName = editUserName.text.toString().trim()
            val strEditPassword = editPassword.text.toString().trim()
            val ok = dbHelper.login(strEditUserName, strEditPassword)

            if(ok){
                Toast.makeText(this, "Bienvenido $strEditUserName", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Usuario o contraseña incorectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}