package models

data class Financa(
    val codigo: Int?,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
    val fkUsuario: Int
)
data class FinancaCriacao(
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
)
