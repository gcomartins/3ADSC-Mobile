package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        supportActionBar?.hide()

        findViewById<Button>(R.id.btSignIn).setOnClickListener {
            GoToSignInPage()
        }
    }

    private fun GoToSignInPage() {
        val signInPage = Intent(this, Login::class.java)
        startActivity(signInPage)
    }
}