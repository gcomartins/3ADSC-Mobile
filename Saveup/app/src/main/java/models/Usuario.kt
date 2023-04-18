package models

import java.util.Date

data class Usuario(
    val email: String,
    val nome: String,
    val senha: String,
    val dataDeNascimento: String,
    val cpf: String,
)
