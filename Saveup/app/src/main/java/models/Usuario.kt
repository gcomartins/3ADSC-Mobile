    package models

import java.util.*

data class Usuario(
    val id: Int,
    val nome: String,
    val email: String,
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
