package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        findViewById<Button>(R.id.btSignIn).setOnClickListener {
//            goToHomePage()
//        }

        findViewById<Button>(R.id.btSignUp).setOnClickListener {
            GoToSignUpPage()
        }
    }

//    private fun goToHomePage() {
//        val homePage = Intent(this, HomePage::class.java)
//        startActivity(homePage)
//    }

     private fun GoToSignUpPage() {
        val signUpPage = Intent(this, Cadastro::class.java)
        startActivity(signUpPage)
    }
}