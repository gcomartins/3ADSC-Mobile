package models

import java.util.*

data class Usuario(
    val email: String,
    val nome: String,
    val senha: String,
    val dataNascimento: Date,
    val cpf: String,
)
data class UsuarioCadastro(
    val email: String,
    val nome: String,
    val senha: String,
    val dataNascimento: String,
)
