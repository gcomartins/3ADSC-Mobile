package models

data class Receita(
    val codigo: Int?,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
    val fkUsuario: Int,
    val frequencia: Int,
)